package com.shinhan.solsolhigh.account.infra;

import com.shinhan.solsolhigh.account.config.MasterBankConfig;
import lombok.AllArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@AllArgsConstructor
public class MasterBankService {
    private final MasterBankConfig masterBankConfig;
    private final AtomicInteger counter;
    private final RestTemplate restTemplate = new RestTemplate();
    private final String bankCode = "088";

    @Transactional
    public Object createBankMember(String email){
        String url = "/member";
        Map<String, Object> body = new HashMap<>();
        body.put("userId", email);
        body.put("apiKey", masterBankConfig.getApiKey());
        return postRequest(url, body);
    }

    @Transactional
    public Object getBankMember(String email){
        String url = "/member/search";
        Map<String, Object> body = new HashMap<>();
        body.put("userId", email);
        body.put("apiKey", masterBankConfig.getApiKey());
        return postRequest(url, body);
    }

    @Transactional
    public Object createDemandDepositProduct(String accountName, String accountDescription){
        String url = "/demandDeposit/createDemandDeposit";
        Map<String, Object> body = generatorBodyWithHeader(url, null);
        body.put("accountName", accountName);
        body.put("accountDescription", accountDescription);
        body.put("bankCode", bankCode);
        return postRequest(url, body);
    }

    @Transactional
    public Object getDemandDepositProducts(){
        String url = "/demandDeposit/inquireDemandDepositList";
        Map<String, Object> body = generatorBodyWithHeader(url, null);
        return postRequest(url, body);
    }

    @Transactional
    public Object createDepositAccount(String userKey, String accountTypeUniqueNo){
        String url = "/demandDeposit/createDemandDepositAccount";
        Map<String, Object> body = generatorBodyWithHeader(url, userKey);
        body.put("accountTypeUniqueNo", accountTypeUniqueNo);
        return postRequest(url, body);
    }

    @Transactional
    public Object getDemandDepositAccounts(String userKey){
        String url = "/demandDeposit/inquireDemandDepositAccountList";
        Map<String, Object> body = generatorBodyWithHeader(url, userKey);
        return postRequest(url, body);
    }

    @Transactional
    public Object getDemandDepositAccount(String userKey, String accountNo){
        String url = "/demandDeposit/inquireDemandDepositAccount";
        Map<String,Object> body = generatorBodyWithHeader(url, userKey);
        body.put("accountNo", accountNo);
        return postRequest(url, body);
    }

    @Transactional
    public Object transferDemandDepositAccount(String userKey,
                                               String depositAccountNo,
                                               Long transactionBalance,
                                               String withdrawalAccountNo,
                                               String depositTransactionSummary,
                                               String withdrawalTransactionSummary){
        String url = "/demandDeposit/updateDemandDepositAccountTransfer";
        Map<String, Object> body = generatorBodyWithHeader(url, userKey);
        body.put("depositAccountNo", depositAccountNo);
        body.put("transactionBalance", transactionBalance);
        body.put("withdrawalAccountNo", withdrawalAccountNo);
        body.put("depositTransactionSummary", depositTransactionSummary);
        body.put("withdrawalTransactionSummary", withdrawalTransactionSummary);
        return postRequest(url, body);
    }

    @Transactional
    public Object getDemandDepositAccountHistory(String userKey,
                                                 String accountNo,
                                                 String startDate,
                                                 String endDate,
                                                 String transactionType,
                                                 String orderByType){
        String url = "/demandDeposit/inquireTransactionHistory";
        Map<String, Object> body = generatorBodyWithHeader(url, userKey);
        body.put("accountNo", accountNo);
        body.put("startDate", startDate);
        body.put("endDate", endDate);
        body.put("transactionType", transactionType);
        body.put("orderByType", orderByType);
        return postRequest(url, body);
    }

    @Transactional
    public Object deleteDemandDepositAccount(String userKey,
                                             String accountNo,
                                             String refundAccountNo){
        String url = "/demandDeposit/deleteDemandDepositAccount";
        Map<String, Object> body = generatorBodyWithHeader(url, userKey);
        body.put("accountNo", accountNo);
        body.put("refundAccountNo", refundAccountNo);
        return postRequest(url, body);
    }

    @Transactional
    public Object createSavingProduct(String accountName,
                                      String accountDescription,
                                      String subscriptionPeriod,
                                      Long minSubscriptionBalance,
                                      Long maxSubscriptionBalance,
                                      Double interestRate,
                                      String rateDescription) {
        String url = "/savings/createProduct";
        Map<String, Object> body = generatorBodyWithHeader(url, null);
        body.put("bankCode", bankCode);
        body.put("accountName", accountName);
        body.put("accountDescription", accountDescription);
        body.put("subscriptionPeriod", subscriptionPeriod);
        body.put("minSubscriptionBalance", minSubscriptionBalance);
        body.put("maxSubscriptionBalance", maxSubscriptionBalance);
        body.put("interestRate", interestRate);
        body.put("rateDescription", rateDescription);
        return postRequest(url, body);
    }

    @Transactional
    public Object getSavingProducts() {
        String url = "/savings/inquireSavingsProducts";
        Map<String, Object> body = generatorBodyWithHeader(url, null);
        return postRequest(url, body);
    }

    @Transactional
    public Object createSavingAccount(String userKey,
                                      String withdrawalAccountNo,
                                      String accountTypeUniqueNo,
                                      Long depositBalance){
        String url = "/savings/createAccount";
        Map<String, Object> body = generatorBodyWithHeader(url, userKey);
        body.put("withdrawalAccountNo", withdrawalAccountNo);
        body.put("accountTypeUniqueNo", accountTypeUniqueNo);
        body.put("depositBalance", depositBalance);
        return postRequest(url, body);
    }

    @Transactional
    public Object getSavingAccounts(String userKey){
        String url = "/savings/inquireAccountList";
        Map<String, Object> body = generatorBodyWithHeader(url, userKey);
        return postRequest(url, body);
    }

    @Transactional
    public Object getSavingAccount(String userKey,
                                   String accountNo){
        String url = "/savings/inquireAccount";
        Map<String, Object> body = generatorBodyWithHeader(url, userKey);
        body.put("accountNo", accountNo);
        return postRequest(url, body);
    }

    @Transactional
    public Object getSavingAccountPayment(String userKey,
                                          String accountNo){
        String url = "/savings/inquirePayment";
        Map<String, Object> body = generatorBodyWithHeader(url, userKey);
        body.put("accountNo", accountNo);
        return postRequest(url, body);
    }

    @Transactional
    public Object getSavingAccountExpiryInterest(String userKey, String accountNo){
        String url = "/savings/inquireExpiryInterest";
        Map<String, Object> body = generatorBodyWithHeader(url, userKey);
        body.put("accountNo", accountNo);
        return postRequest(url, body);
    }

    @Transactional
    public Object getSavingAccountEarlyTerminationInterest(String userKey, String accountNo){
        String url = "/savings/inquireEarlyTerminationInterest";
        Map<String, Object> body = generatorBodyWithHeader(url, userKey);
        body.put("accountNo", accountNo);
        return postRequest(url, body);
    }

    @Transactional
    public Object deleteSavingAccount(String userKey, String accountNo){
        String url = "/savings/deleteAccount";
        Map<String, Object> body = generatorBodyWithHeader(url, userKey);
        body.put("accountNo", accountNo);
        return postRequest(url, body);
    }

    private ResponseEntity<?> postRequest(String url, Map<String, Object> body){
        RequestEntity<?> requestEntity = RequestEntity
                .post(masterBankConfig.getBase()+url)
                .body(body);
        return restTemplate.exchange(requestEntity, Map.class);
    }

    private Map<String, Object> generatorBodyWithHeader(String url, String userKey){
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> header = new HashMap<>();

        header.put("apiName", url.substring(url.lastIndexOf("/")+1));
        header.put("transmissionDate", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        header.put("transmissionTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmmss")));
        header.put("institutionCode", masterBankConfig.getInstitutionCode());
        header.put("fintechAppNo", masterBankConfig.getFintechAppNo());
        header.put("apiServiceCode", header.get("apiName"));
        header.put("institutionTransactionUniqueNo", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
                +String.format("%06d", generatorInstitutionTransactionUniqueNo()));
        header.put("apiKey", masterBankConfig.getApiKey());
        if(userKey != null)
            header.put("userKey", userKey);
        result.put("Header", header);
        return result;
    }

    private int generatorInstitutionTransactionUniqueNo(){
        int value = counter.getAndIncrement();
        if(value == 99999)
            counter.set(0);
        return value;
    }
}