package Complaint.scheduled;

import Complaint.enums.ApprovalStatus;
import Complaint.enums.ComplaintProcessingState;
import Complaint.enums.TransferRecallType;
import Complaint.model.Complaint;
import Complaint.model.Exceptions;
import Complaint.service.ComplaintServiceImpl;
import Complaint.service.ExceptionsServiceImpl;
import Complaint.utilities.ApiService;
import Complaint.utilities.AuthUtil;
import com.teamapt.exceptions.CosmosServiceException;
import com.teamapt.finacle.poster.lib.constants.CustomerDebitMethod;
import com.teamapt.finacle.poster.lib.constants.FeeMethod;
import com.teamapt.finacle.poster.lib.constants.UploadType;
import com.teamapt.finacle.poster.lib.model.api.ApiUploadRequest;
import com.teamapt.finacle.poster.lib.model.api.BulkUploadRequest;
import com.teamapt.finacle.poster.lib.model.api.CFileEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@Component
public class RecallIntraFundsHandler {

    private Logger logger = LoggerFactory.getLogger(RecallMailSendingHandler.class);
    private boolean isDev;
    private ComplaintServiceImpl complaintService;
    private ExceptionsServiceImpl exceptionsService;
    private AuthUtil authUtil;
    private String url;

    @Scheduled(fixedRateString = "${recall.intra.funds.interval}")
    public void sendEmailToBeneficiaryBank(){

        if(isDev)
            return;

        List<Complaint> intraRecallComplaintList = complaintService.
                getComplaintByProcessingStateAndRecallTypeAndApprovalStatus(
                        ComplaintProcessingState.NEW.getValue(),
                        TransferRecallType.INTRA.getCode(),
                        ApprovalStatus.APPROVED.getApprovalStatus());

        final Stream<Complaint> complaintStream = intraRecallComplaintList
                                                    .stream()
                                                    .filter(e -> e.getComplaintState().getRetryDebitAccountCount() > 16)
                                                    .filter(e -> !e.getComplaintState().getIsComplainantAccountCredited() &&
                                                                  !e.getComplaintState().getIsDefendantAccountDebited());

        complaintStream.forEach(complaint -> {
            BulkUploadRequest bulkUploadRequest = createBulkUploadRequest(complaint);
            String token = "yhdheyxccedr"; //authUtil.getAccessToken();

            try {
                ResponseEntity response =
                        ApiService.exchange(ApiService.getUri(url),
                                HttpMethod.POST,
                                ApiService.generateRequest(bulkUploadRequest, ApiService
                                        .setAuthorizationHeader(ApiService.getHeaders(), token)));

                if(response.getStatusCode().is2xxSuccessful() && !response.getBody().toString().contains("400 BAD_REQUEST")) {
                    try{
                        updateComplaint(complaint);
                        complaintService.saveComplaint(complaint);
                    }catch(Exception ex){
                        String exceptionMessage = "An Error Occurred While Updating or Saving complaint ".concat(ex.getMessage());
                        exceptionsService.saveException(new Exceptions(0l, String.format("%s", exceptionMessage),
                                "RecallIntraFundsHandler", new Date()));
                        throw new CosmosServiceException(exceptionMessage);
                    }
                }else {
                    String message = String.format("%s%s%s%s","An Error While Exceuting Fund Recall for ",
                            complaint.getTrackingNumber(), " with {} " + response.getBody());
                    exceptionsService.saveException(new Exceptions(0l, message,
                            "RecallIntraFundsHandler", new Date()));
                    throw new CosmosServiceException(message);
                }
            } catch (Exception e) {
                logger.info("Error occured while initiating bulk transfer");
            }
        });
    }

    private void updateComplaint(Complaint complaint){
        complaint.getComplaintState().setIsDefendantAccountDebited(true);
        complaint.getComplaintState().setIsComplainantAccountCredited(true);
        complaint.getComplaintState().setAmountCreditedToCustomer(complaint.getAmountToBeRecalled());
        complaint.getComplaintState().setAmountDebitedFromDefendat(complaint.getAmountToBeRecalled());
    }

    private BulkUploadRequest createBulkUploadRequest(Complaint complaint){
        ApiUploadRequest bulkUploadRequest = new ApiUploadRequest();

        bulkUploadRequest.setClientAccountNumber(complaint.getBeneficiaryAccountNumber());
        bulkUploadRequest.setClientAccountName(complaint.getBeneficiaryName());
        bulkUploadRequest.setCustomerDebitMethod(CustomerDebitMethod.BULK);
        bulkUploadRequest.setChargingProfileKey("ONLINE_BANKING");
        bulkUploadRequest.setUploadType(UploadType.TRANSFERS.toString());
        bulkUploadRequest.setBulkNarration("FUND REVERSAL FOR " + complaint.getComplaintTransaction().getAccountName());
        bulkUploadRequest.setIgnoreDuplicate(true);
        bulkUploadRequest.setFeeMethod(FeeMethod.BENEFICIARY);

        List<CFileEntry> transactions = new ArrayList<>();

        CFileEntry transaction = new CFileEntry();
        transaction.setBeneficiaryBankCode("000007");
        transaction.setBeneficiaryName(complaint.getComplaintTransaction().getAccountName());
        transaction.setDestinationAccount(complaint.getComplaintTransaction().getAccountNumber());
        transaction.setParticulars("FUND REVERSAL OF " + complaint.getAmountToBeRecalled() + " From " + complaint.getBeneficiaryName());
        transaction.setAmount(complaint.getAmountToBeRecalled());
        transaction.setFee(new BigDecimal(0.0));
        transaction.setRefId(transaction.calculateRef());

        transactions.add(transaction);
        bulkUploadRequest.setTransactions(transactions);

        return bulkUploadRequest;
    }

    @Autowired
    public void setDev(@Value("${app.is.dev:false}") boolean dev) {
        isDev = dev;
    }

    @Autowired
    public void setUrl(@Value("${bulk.upload.url}") String url) {
        this.url = url;
    }

    @Autowired
    public void setComplaintService(ComplaintServiceImpl complaintService) {
        this.complaintService = complaintService;
    }

    @Autowired
    public void setExceptionsService(ExceptionsServiceImpl exceptionsService) {
        this.exceptionsService = exceptionsService;
    }

    @Autowired
    public void setAuthUtil(AuthUtil authUtil) {
        this.authUtil = authUtil;
    }
}
