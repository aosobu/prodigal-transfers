package Complaint.Repository;

import Complaint.DTO.ComplaintTransferQueryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplaintTransferRepo extends JpaRepository<ComplaintTransferQueryModel,Long> {
@Query(value = "select ct.id, ct.comment, ct.currency_code, ct.custom_reference, ct.extra_information, ct.minor_amount, ct.minor_fee_amount, ct.minor_vat_amount, ct.name_enquiry_reference, ct.narration, ct.response_code, ct.sink_account_name, ct.sink_account_number, ct.sink_account_provider_code, ct.sink_account_provider_name, ct.source_account_number, ct.source_account_provider_code, ct.source_account_provider_name, ct.status, ct.time_added, ct.time_executed, ct.transaction_id, ct.transaction_status, ct.transaction_type, ct.transfer_batch_key, ct.validated_sink_account_name, ct.customer_id, ct.reversed, ct.fromBVN, ct.toBVN, cc.bank_branch_id, cc.active "
       + "from transfer ct left join customer_account cc on ct.customer_id = cc.customer_id and ct.customer_id = ?1 and ct.transaction_id = ?2 ", nativeQuery = true)
    public ComplaintTransferQueryModel ComplaintTransferDetails(@Param("customer_id") String customer_id, @Param("transaction_id") String transaction_id);
}
