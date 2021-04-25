package Complaint.DTO;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;
@Entity
public class ComplaintTransferQueryModel {
    @Id
    private Long id;
    private String comment;
    private String currency_code;
    private String custom_reference;
    private String extra_information;
    private String minor_amount;
    private String minor_fee_amount;
    private String minor_vat_amount;
    private String name_enquiry_reference;
    private String narration;
    private String response_code;
    private String sink_account_name;
    private String sink_account_number;
    private String sink_account_provider_code;
    private String sink_account_provider_name;
    private String source_account_number;
    private String source_account_provider_code;
    private String source_account_provider_name;
    private String status;
    private Timestamp time_added;
    private Timestamp time_executed;
    private String transaction_id;
    private String transaction_status;
    private String transaction_type;
    private String transfer_batch_key;
    private String validated_sink_account_name;
    private String customer_id;
    private boolean reversed;
    private String fromBVN;
    private String toBVN;
    private String bank_branch_id;
    private boolean active;

    public ComplaintTransferQueryModel() {
    }

    public ComplaintTransferQueryModel(Long id, String comment, String currency_code, String custom_reference, String extra_information, String minor_amount, String minor_fee_amount, String minor_vat_amount, String name_enquiry_reference, String narration, String response_code, String sink_account_name, String sink_account_number, String sink_account_provider_code, String sink_account_provider_name, String source_account_number, String source_account_provider_code, String source_account_provider_name, String status, Timestamp time_added, Timestamp time_executed, String transaction_id, String transaction_status, String transaction_type, String transfer_batch_key, String validated_sink_account_name, String customer_id, boolean reversed, String fromBVN, String toBVN, String bank_branch_id, boolean active) {
        this.id = id;
        this.comment = comment;
        this.currency_code = currency_code;
        this.custom_reference = custom_reference;
        this.extra_information = extra_information;
        this.minor_amount = minor_amount;
        this.minor_fee_amount = minor_fee_amount;
        this.minor_vat_amount = minor_vat_amount;
        this.name_enquiry_reference = name_enquiry_reference;
        this.narration = narration;
        this.response_code = response_code;
        this.sink_account_name = sink_account_name;
        this.sink_account_number = sink_account_number;
        this.sink_account_provider_code = sink_account_provider_code;
        this.sink_account_provider_name = sink_account_provider_name;
        this.source_account_number = source_account_number;
        this.source_account_provider_code = source_account_provider_code;
        this.source_account_provider_name = source_account_provider_name;
        this.status = status;
        this.time_added = time_added;
        this.time_executed = time_executed;
        this.transaction_id = transaction_id;
        this.transaction_status = transaction_status;
        this.transaction_type = transaction_type;
        this.transfer_batch_key = transfer_batch_key;
        this.validated_sink_account_name = validated_sink_account_name;
        this.customer_id = customer_id;
        this.reversed = reversed;
        this.fromBVN = fromBVN;
        this.toBVN = toBVN;
        this.bank_branch_id = bank_branch_id;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCurrency_code() {
        return currency_code;
    }

    public void setCurrency_code(String currency_code) {
        this.currency_code = currency_code;
    }

    public String getCustom_reference() {
        return custom_reference;
    }

    public void setCustom_reference(String custom_reference) {
        this.custom_reference = custom_reference;
    }

    public String getExtra_information() {
        return extra_information;
    }

    public void setExtra_information(String extra_information) {
        this.extra_information = extra_information;
    }

    public String getMinor_amount() {
        return minor_amount;
    }

    public void setMinor_amount(String minor_amount) {
        this.minor_amount = minor_amount;
    }

    public String getMinor_fee_amount() {
        return minor_fee_amount;
    }

    public void setMinor_fee_amount(String minor_fee_amount) {
        this.minor_fee_amount = minor_fee_amount;
    }

    public String getMinor_vat_amount() {
        return minor_vat_amount;
    }

    public void setMinor_vat_amount(String minor_vat_amount) {
        this.minor_vat_amount = minor_vat_amount;
    }

    public String getName_enquiry_reference() {
        return name_enquiry_reference;
    }

    public void setName_enquiry_reference(String name_enquiry_reference) {
        this.name_enquiry_reference = name_enquiry_reference;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public String getResponse_code() {
        return response_code;
    }

    public void setResponse_code(String response_code) {
        this.response_code = response_code;
    }

    public String getSink_account_name() {
        return sink_account_name;
    }

    public void setSink_account_name(String sink_account_name) {
        this.sink_account_name = sink_account_name;
    }

    public String getSink_account_number() {
        return sink_account_number;
    }

    public void setSink_account_number(String sink_account_number) {
        this.sink_account_number = sink_account_number;
    }

    public String getSink_account_provider_code() {
        return sink_account_provider_code;
    }

    public void setSink_account_provider_code(String sink_account_provider_code) {
        this.sink_account_provider_code = sink_account_provider_code;
    }

    public String getSink_account_provider_name() {
        return sink_account_provider_name;
    }

    public void setSink_account_provider_name(String sink_account_provider_name) {
        this.sink_account_provider_name = sink_account_provider_name;
    }

    public String getSource_account_number() {
        return source_account_number;
    }

    public void setSource_account_number(String source_account_number) {
        this.source_account_number = source_account_number;
    }

    public String getSource_account_provider_code() {
        return source_account_provider_code;
    }

    public void setSource_account_provider_code(String source_account_provider_code) {
        this.source_account_provider_code = source_account_provider_code;
    }

    public String getSource_account_provider_name() {
        return source_account_provider_name;
    }

    public void setSource_account_provider_name(String source_account_provider_name) {
        this.source_account_provider_name = source_account_provider_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getTime_added() {
        return time_added;
    }

    public void setTime_added(Timestamp time_added) {
        this.time_added = time_added;
    }

    public Timestamp getTime_executed() {
        return time_executed;
    }

    public void setTime_executed(Timestamp time_executed) {
        this.time_executed = time_executed;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getTransaction_status() {
        return transaction_status;
    }

    public void setTransaction_status(String transaction_status) {
        this.transaction_status = transaction_status;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }

    public String getTransfer_batch_key() {
        return transfer_batch_key;
    }

    public void setTransfer_batch_key(String transfer_batch_key) {
        this.transfer_batch_key = transfer_batch_key;
    }

    public String getValidated_sink_account_name() {
        return validated_sink_account_name;
    }

    public void setValidated_sink_account_name(String validated_sink_account_name) {
        this.validated_sink_account_name = validated_sink_account_name;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public boolean isReversed() {
        return reversed;
    }

    public void setReversed(boolean reversed) {
        this.reversed = reversed;
    }

    public String getFromBVN() {
        return fromBVN;
    }

    public void setFromBVN(String fromBVN) {
        this.fromBVN = fromBVN;
    }

    public String getToBVN() {
        return toBVN;
    }

    public void setToBVN(String toBVN) {
        this.toBVN = toBVN;
    }

    public String getBank_branch_id() {
        return bank_branch_id;
    }

    public void setBank_branch_id(String bank_branch_id) {
        this.bank_branch_id = bank_branch_id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "ComplaintTransferQueryModel{" +
                "id=" + id +
                ", comment='" + comment + '\'' +
                ", currency_code='" + currency_code + '\'' +
                ", custom_reference='" + custom_reference + '\'' +
                ", extra_information='" + extra_information + '\'' +
                ", minor_amount='" + minor_amount + '\'' +
                ", minor_fee_amount='" + minor_fee_amount + '\'' +
                ", minor_vat_amount='" + minor_vat_amount + '\'' +
                ", name_enquiry_reference='" + name_enquiry_reference + '\'' +
                ", narration='" + narration + '\'' +
                ", response_code='" + response_code + '\'' +
                ", sink_account_name='" + sink_account_name + '\'' +
                ", sink_account_number='" + sink_account_number + '\'' +
                ", sink_account_provider_code='" + sink_account_provider_code + '\'' +
                ", sink_account_provider_name='" + sink_account_provider_name + '\'' +
                ", source_account_number='" + source_account_number + '\'' +
                ", source_account_provider_code='" + source_account_provider_code + '\'' +
                ", source_account_provider_name='" + source_account_provider_name + '\'' +
                ", status='" + status + '\'' +
                ", time_added=" + time_added +
                ", time_executed=" + time_executed +
                ", transaction_id='" + transaction_id + '\'' +
                ", transaction_status='" + transaction_status + '\'' +
                ", transaction_type='" + transaction_type + '\'' +
                ", transfer_batch_key='" + transfer_batch_key + '\'' +
                ", validated_sink_account_name='" + validated_sink_account_name + '\'' +
                ", customer_id='" + customer_id + '\'' +
                ", reversed=" + reversed +
                ", fromBVN='" + fromBVN + '\'' +
                ", toBVN='" + toBVN + '\'' +
                ", bank_branch_id='" + bank_branch_id + '\'' +
                ", active=" + active +
                '}';
    }
}
