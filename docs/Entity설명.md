# Entity 설계 및 설명

# 구매처

### 종속 관계

```scss
Vendor (구매처 정보)
├─ vendorCode  // 구매처번호
├─ name  // 구매처명
├─ personalId  // 개인번호
├─ businessRegistrationNo  // 사업자번호
├─ vendorGroupCode  // 구매처그룹
├─ countryCode  // 국가코드
├─ address  // 주소
├─> **VendorCompany (회사 코드 정보)**
└─> **VendorPurchasing (구매 조직 정보. 1:N)**
				 
```

```scss
VendorCompany (회사 코드 정보)
  ├─ companyCode  // 회사코드
  ├─ accountCode  // 계정
  └─ paymentTermCode  // 지급조건
```

```scss
VendorPurchasing (구매 조직 정보)
   ├─ purchasingOrgCode  // 구매조직(부서)
   ├─ purchasingGroupCode  // 구매그룹(담당팀)
   ├─ currency  // 구매오더 통화
   └─ taxCode  // 세금 코드
```

**Vendor : VendorCompany 의 관계는 1 : N**

**이유**
- 회사 코드는 본사 내의 내부 법인단위이므로, 
지사에 따라서 다르게 할당해야하므로 1:N 관계로 설계하였습니다.
- 예시) 아래 표 참조

| 구매처 | 회사코드 |
| --- | --- |
| 신세계푸드 | 서울지사 (1000) |
| 신세계푸드 | 부산지사 (2000) |
| 신세계푸드 | 해외법인 (3000) |

---

**Vendor : VendorPuchasing 의 관계는 1 : N**

**이유**
- 동일한 구매처에 대해서 구매하는 물품이 구매 조직별로 상이할 수 있으므로 1:N의 관계로 연관하였습니다.