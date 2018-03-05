package com.nami.boot.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Assets implements Serializable {
    private Integer id;

    private String businessCode;

    private String businessTransRef;

    private BigDecimal assetsAmount;

    private Integer phase;

    private String phaseUnit;

    private String currency;

    private String startDate;

    private String expireDate;

    private String clientName;

    private String clientIdCard;

    private Date commitTime;

    private Date editTime;

    private Integer dfAssetsPackageId;

    private Integer numberOfExhibitionPeriod;

    private String clientMobile;

    private String clientId;

    private String clientCertificateType;

    private String clientEducationBackground;

    private String clientMaritalStatus;

    private String status;

    private String overRaisedCause;

    private Date accountingDate;

    private Date repaymentDate;

    private String causeOfFailur;

    private String assetTransResult;

    private BigDecimal interest;

    private String assetRepaymentResult;

    private Date createTime;

    private Date updateTime;

    private BigDecimal coverCharge;

    private BigDecimal payAmount;

    private String drawalsStatus;

    private String gatewayPayStatus;

    private String repaymentStatus;

    private Date refundTime;

    private Date gatewayPayTime;

    private Date drawalsTime;

    private String bankCardNo;

    private String loanBizId;

    private String orderFundStatus;

    private String bankId;

    private String idCardPic;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode == null ? null : businessCode.trim();
    }

    public String getBusinessTransRef() {
        return businessTransRef;
    }

    public void setBusinessTransRef(String businessTransRef) {
        this.businessTransRef = businessTransRef == null ? null : businessTransRef.trim();
    }

    public BigDecimal getAssetsAmount() {
        return assetsAmount;
    }

    public void setAssetsAmount(BigDecimal assetsAmount) {
        this.assetsAmount = assetsAmount;
    }

    public Integer getPhase() {
        return phase;
    }

    public void setPhase(Integer phase) {
        this.phase = phase;
    }

    public String getPhaseUnit() {
        return phaseUnit;
    }

    public void setPhaseUnit(String phaseUnit) {
        this.phaseUnit = phaseUnit == null ? null : phaseUnit.trim();
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency == null ? null : currency.trim();
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate == null ? null : startDate.trim();
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate == null ? null : expireDate.trim();
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName == null ? null : clientName.trim();
    }

    public String getClientIdCard() {
        return clientIdCard;
    }

    public void setClientIdCard(String clientIdCard) {
        this.clientIdCard = clientIdCard == null ? null : clientIdCard.trim();
    }

    public Date getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(Date commitTime) {
        this.commitTime = commitTime;
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public Integer getDfAssetsPackageId() {
        return dfAssetsPackageId;
    }

    public void setDfAssetsPackageId(Integer dfAssetsPackageId) {
        this.dfAssetsPackageId = dfAssetsPackageId;
    }

    public Integer getNumberOfExhibitionPeriod() {
        return numberOfExhibitionPeriod;
    }

    public void setNumberOfExhibitionPeriod(Integer numberOfExhibitionPeriod) {
        this.numberOfExhibitionPeriod = numberOfExhibitionPeriod;
    }

    public String getClientMobile() {
        return clientMobile;
    }

    public void setClientMobile(String clientMobile) {
        this.clientMobile = clientMobile == null ? null : clientMobile.trim();
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId == null ? null : clientId.trim();
    }

    public String getClientCertificateType() {
        return clientCertificateType;
    }

    public void setClientCertificateType(String clientCertificateType) {
        this.clientCertificateType = clientCertificateType == null ? null : clientCertificateType.trim();
    }

    public String getClientEducationBackground() {
        return clientEducationBackground;
    }

    public void setClientEducationBackground(String clientEducationBackground) {
        this.clientEducationBackground = clientEducationBackground == null ? null : clientEducationBackground.trim();
    }

    public String getClientMaritalStatus() {
        return clientMaritalStatus;
    }

    public void setClientMaritalStatus(String clientMaritalStatus) {
        this.clientMaritalStatus = clientMaritalStatus == null ? null : clientMaritalStatus.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getOverRaisedCause() {
        return overRaisedCause;
    }

    public void setOverRaisedCause(String overRaisedCause) {
        this.overRaisedCause = overRaisedCause == null ? null : overRaisedCause.trim();
    }

    public Date getAccountingDate() {
        return accountingDate;
    }

    public void setAccountingDate(Date accountingDate) {
        this.accountingDate = accountingDate;
    }

    public Date getRepaymentDate() {
        return repaymentDate;
    }

    public void setRepaymentDate(Date repaymentDate) {
        this.repaymentDate = repaymentDate;
    }

    public String getCauseOfFailur() {
        return causeOfFailur;
    }

    public void setCauseOfFailur(String causeOfFailur) {
        this.causeOfFailur = causeOfFailur == null ? null : causeOfFailur.trim();
    }

    public String getAssetTransResult() {
        return assetTransResult;
    }

    public void setAssetTransResult(String assetTransResult) {
        this.assetTransResult = assetTransResult == null ? null : assetTransResult.trim();
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public String getAssetRepaymentResult() {
        return assetRepaymentResult;
    }

    public void setAssetRepaymentResult(String assetRepaymentResult) {
        this.assetRepaymentResult = assetRepaymentResult == null ? null : assetRepaymentResult.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public BigDecimal getCoverCharge() {
        return coverCharge;
    }

    public void setCoverCharge(BigDecimal coverCharge) {
        this.coverCharge = coverCharge;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public String getDrawalsStatus() {
        return drawalsStatus;
    }

    public void setDrawalsStatus(String drawalsStatus) {
        this.drawalsStatus = drawalsStatus == null ? null : drawalsStatus.trim();
    }

    public String getGatewayPayStatus() {
        return gatewayPayStatus;
    }

    public void setGatewayPayStatus(String gatewayPayStatus) {
        this.gatewayPayStatus = gatewayPayStatus == null ? null : gatewayPayStatus.trim();
    }

    public String getRepaymentStatus() {
        return repaymentStatus;
    }

    public void setRepaymentStatus(String repaymentStatus) {
        this.repaymentStatus = repaymentStatus == null ? null : repaymentStatus.trim();
    }

    public Date getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(Date refundTime) {
        this.refundTime = refundTime;
    }

    public Date getGatewayPayTime() {
        return gatewayPayTime;
    }

    public void setGatewayPayTime(Date gatewayPayTime) {
        this.gatewayPayTime = gatewayPayTime;
    }

    public Date getDrawalsTime() {
        return drawalsTime;
    }

    public void setDrawalsTime(Date drawalsTime) {
        this.drawalsTime = drawalsTime;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo == null ? null : bankCardNo.trim();
    }

    public String getLoanBizId() {
        return loanBizId;
    }

    public void setLoanBizId(String loanBizId) {
        this.loanBizId = loanBizId == null ? null : loanBizId.trim();
    }

    public String getOrderFundStatus() {
        return orderFundStatus;
    }

    public void setOrderFundStatus(String orderFundStatus) {
        this.orderFundStatus = orderFundStatus == null ? null : orderFundStatus.trim();
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId == null ? null : bankId.trim();
    }

    public String getIdCardPic() {
        return idCardPic;
    }

    public void setIdCardPic(String idCardPic) {
        this.idCardPic = idCardPic == null ? null : idCardPic.trim();
    }
}