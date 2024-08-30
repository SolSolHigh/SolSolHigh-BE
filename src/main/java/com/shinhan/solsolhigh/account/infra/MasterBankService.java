package com.shinhan.solsolhigh.account.infra;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shinhan.solsolhigh.account.application.*;
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
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@AllArgsConstructor
public class MasterBankService {

    private final ObjectMapper objectMapper;
    private final MasterBankConfig masterBankConfig;
    private final AtomicInteger counter;
    private final RestTemplate restTemplate = new RestTemplate();
    private final String bankCode = "088";

    @Transactional
    public String createBankMember(String email){
        String url = "/member";
        Map<String, Object> body = new HashMap<>();
        body.put("userId", email);
        body.put("apiKey", masterBankConfig.getApiKey());
        ResponseEntity<Map> response = postRequest(url, body);
        return (String) response.getBody().get("userKey");
    }

    @Transactional
    public String getBankMember(String email){
        String url = "/member/search";
        Map<String, Object> body = new HashMap<>();
        body.put("userId", email);
        body.put("apiKey", masterBankConfig.getApiKey());
        ResponseEntity<Map> response = postRequest(url, body);
        return (String) response.getBody().get("userKey");
    }

    @Transactional
    public Object createDemandDepositProduct(String accountName, String accountDescription){
        String url = "/edu/demandDeposit/createDemandDeposit";
        Map<String, Object> body = generatorBodyWithHeader(url, null);
        body.put("accountName", accountName);
        body.put("accountDescription", accountDescription);
        body.put("bankCode", bankCode);
        return postRequest(url, body);
    }

    @Transactional
    public List<DemandDepositProductDto> getDemandDepositProducts(){
        String url = "/edu/demandDeposit/inquireDemandDepositList";
        Map<String, Object> body = generatorBodyWithHeader(url, null);
        ResponseEntity<Map> response = postRequest(url, body);
        List<DemandDepositProductDto> result = objectMapper.convertValue(
                response.getBody().get("REC"),
                new TypeReference<>() {});
        return result;
    }

    @Transactional
    public String createDepositAccount(String userKey, String accountTypeUniqueNo){
        String url = "/edu/demandDeposit/createDemandDepositAccount";
        Map<String, Object> body = generatorBodyWithHeader(url, userKey);
        body.put("accountTypeUniqueNo", accountTypeUniqueNo);
        ResponseEntity<Map> response = postRequest(url, body);
        Map<String, Object> sub = (Map<String, Object>)(response.getBody().get("REC"));
        return sub.get("accountNo").toString();
    }

    @Transactional
    public DemandDepositDetail getDemandDepositAccount(String userKey, String accountNo){
        String url = "/edu/demandDeposit/inquireDemandDepositAccount";
        Map<String,Object> body = generatorBodyWithHeader(url, userKey);
        body.put("accountNo", accountNo);
        ResponseEntity<Map> response = postRequest(url, body);
        DemandDepositDetail result = objectMapper.convertValue(
                response.getBody().get("REC"),
                new TypeReference<>() {});
        return result;
    }

    @Transactional
    public DepositDetail getDepositAccount(String userKey, String accountNo){
        String url = "/edu/demandDeposit/inquireDemandDepositAccount";
        Map<String,Object> body = generatorBodyWithHeader(url, userKey);
        body.put("accountNo", accountNo);
        ResponseEntity<Map> response = postRequest(url, body);
        DepositDetail result = objectMapper.convertValue(
                response.getBody().get("REC"),
                new TypeReference<>() {});
        return result;
    }


    @Transactional
    public Object transferDemandDepositAccount(String userKey,
                                               String depositAccountNo,
                                               Long transactionBalance,
                                               String withdrawalAccountNo,
                                               String depositTransactionSummary,
                                               String withdrawalTransactionSummary){
        String url = "/edu/demandDeposit/updateDemandDepositAccountTransfer";
        Map<String, Object> body = generatorBodyWithHeader(url, userKey);
        body.put("depositAccountNo", depositAccountNo);
        body.put("transactionBalance", transactionBalance);
        body.put("withdrawalAccountNo", withdrawalAccountNo);
        body.put("depositTransactionSummary", depositTransactionSummary);
        body.put("withdrawalTransactionSummary", withdrawalTransactionSummary);
        return postRequest(url, body);
    }

    @Transactional
    public List<DemandDepositAccountTransaction> getDemandDepositAccountHistory(String userKey,
                                                              String accountNo,
                                                              String startDate,
                                                              String endDate){
        String url = "/edu/demandDeposit/inquireTransactionHistoryList";
        Map<String, Object> body = generatorBodyWithHeader(url, userKey);
        body.put("accountNo", accountNo);
        body.put("startDate", startDate);
        body.put("endDate", endDate);
        body.put("transactionType", "A");
        body.put("orderByType", "DESC");
        ResponseEntity<Map> response = postRequest(url, body);
        Map<String, Object> sub = (Map<String, Object>)(response.getBody().get("REC"));
        List<DemandDepositAccountTransaction> result = objectMapper.convertValue(
                sub.get("list"),
                new TypeReference<>() {});
        return result;
    }

    @Transactional
    public Object deleteDemandDepositAccount(String userKey,
                                             String accountNo,
                                             String refundAccountNo){
        String url = "/edu/demandDeposit/deleteDemandDepositAccount";
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
        String url = "/edu/savings/createProduct";
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
    public List<SavingProductDto> getSavingProducts() {
        String url = "/edu/savings/inquireSavingsProducts";
        Map<String, Object> body = generatorBodyWithHeader(url, null);
        ResponseEntity<Map> response = postRequest(url, body);
        List<SavingProductDto> result = objectMapper.convertValue(
                response.getBody().get("REC"),
                new TypeReference<>() {});
        return result;
    }

    @Transactional
    public String createSavingAccount(String userKey,
                                      String withdrawalAccountNo,
                                      String accountTypeUniqueNo,
                                      Long depositBalance){
        String url = "/edu/savings/createAccount";
        Map<String, Object> body = generatorBodyWithHeader(url, userKey);
        body.put("withdrawalAccountNo", withdrawalAccountNo);
        body.put("accountTypeUniqueNo", accountTypeUniqueNo);
        body.put("depositBalance", depositBalance);
        ResponseEntity<Map> response = postRequest(url, body);
        Map<String, Object> sub = (Map<String, Object>)(response.getBody().get("REC"));
        return sub.get("accountNo").toString();
    }

    @Transactional
    public SavingDetail getSavingAccount(String userKey,
                                         String accountNo){
        String url = "/edu/savings/inquireAccount";
        Map<String, Object> body = generatorBodyWithHeader(url, userKey);
        body.put("accountNo", accountNo);
        ResponseEntity<Map> response = postRequest(url, body);
        SavingDetail result = objectMapper.convertValue(
                response.getBody().get("REC"),
                new TypeReference<>() {});
        return result;
    }

    @Transactional
    public Object getSavingAccountPayment(String userKey,
                                          String accountNo){
        String url = "/edu/savings/inquirePayment";
        Map<String, Object> body = generatorBodyWithHeader(url, userKey);
        body.put("accountNo", accountNo);
        return postRequest(url, body);
    }

    @Transactional
    public Object getSavingAccountExpiryInterest(String userKey, String accountNo){
        String url = "/edu/savings/inquireExpiryInterest";
        Map<String, Object> body = generatorBodyWithHeader(url, userKey);
        body.put("accountNo", accountNo);
        return postRequest(url, body);
    }

    @Transactional
    public Object getSavingAccountEarlyTerminationInterest(String userKey, String accountNo){
        String url = "/edu/savings/inquireEarlyTerminationInterest";
        Map<String, Object> body = generatorBodyWithHeader(url, userKey);
        body.put("accountNo", accountNo);
        return postRequest(url, body);
    }

    @Transactional
    public Object deleteSavingAccount(String userKey, String accountNo){
        String url = "/edu/savings/deleteAccount";
        Map<String, Object> body = generatorBodyWithHeader(url, userKey);
        body.put("accountNo", accountNo);
        return postRequest(url, body);
    }

    @Transactional
    public Object plusAccountMoney(String userKey, String accountNo, Long transactionBalance, String transactionSummary){
        String url = "/edu/demandDeposit/updateDemandDepositAccountDeposit";
        Map<String, Object> body = generatorBodyWithHeader(url, userKey);
        body.put("accountNo", accountNo);
        body.put("transactionBalance", transactionBalance);
        body.put("transactionSummary", transactionSummary);
        return postRequest(url, body);
    }

    @Transactional
    public Object minusAccountMoney(String userKey, String accountNo, Long transactionBalance, String transactionSummary){
        String url = "/edu/demandDeposit/updateDemandDepositAccountWithdrawal";
        Map<String, Object> body = generatorBodyWithHeader(url, userKey);
        body.put("accountNo", accountNo);
        body.put("transactionBalance", transactionBalance);
        body.put("transactionSummary", transactionSummary);
        return postRequest(url, body);
    }

    private ResponseEntity<Map> postRequest(String url, Map<String, Object> body){
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