package com.example.rest.repository;

import com.example.rest.constant.Status;
import com.example.rest.data_interface.BalanceInterface;
import com.example.rest.entity.RootEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface RestTelegramRepository extends JpaRepository<RootEntity, Long> {

    @Query(nativeQuery = true, value = "select * from get_receipt_cash(:currency_id, :from_date, :to_date)")
    Optional<Double> getReceiptCash(
            @Param("currency_id") Integer currencyId,
            @Param("from_date") LocalDate fromDate,
            @Param("to_date")   LocalDate toDate);


    @Query(nativeQuery = true, value = "select * from get_payment_cash(:currency_id, :from_date, :to_date)")
    Optional<Double> getPaymentCash(
            @Param("currency_id") Integer currencyId,
            @Param("from_date") LocalDate fromDate,
            @Param("to_date") LocalDate toDate);

    @Query(nativeQuery = true, value = "select * from get_receipt_bank(:currency_id, :from_date, :to_date)")
    Optional<Double> getReceiptBank(
            @Param("currency_id") Integer currencyId,
            @Param("from_date") LocalDate fromDate,
            @Param("to_date") LocalDate toDate);

    @Query(nativeQuery = true, value = "select * from get_payment_bank(:currency_id, :from_date, :to_date)")
    Optional<Double> getPaymentBank(
            @Param("currency_id") Integer currencyId,
            @Param("from_date") LocalDate fromDate,
            @Param("to_date") LocalDate toDate);

    @Query(nativeQuery = true, value = "select * from get_total_returned_amount_from_client(:currency_id, :from_date, :to_date)")
    Optional<Double> getTotalReturnedAmountFromClient(
            @Param("currency_id") Integer currencyId,
            @Param("from_date") LocalDate fromDate,
            @Param("to_date") LocalDate toDate);

    @Query(nativeQuery = true, value = "select * from get_total_balance_client(:currency_id, :to_date)")
    Optional<BalanceInterface> getTotalBalanceClient(
                @Param("currency_id") Integer currencyId,
                @Param("to_date") LocalDate toDate);


    @Procedure(value = "save_user_model")
    void save(
            @Param("user_name")  String userName,
            @Param("first_name") String firstName,
            @Param("chat_id")    Long chatId,
            @Param("status")     Status status,
            @Param("contact")    String contact,
            @Param("last_name")  String lastName);

}
