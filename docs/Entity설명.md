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

# 구매 오더
### 종속관계

```scss
PurchaseOrderHeader (구매오더 헤더)
├─ id                   // 구매오더 ID
├─ orderDate            // 주문일자 / 증빙일자
└─▶ **Vendor**              // 구매처 참조 (N:1)
```

```scss
PurchaseOrderItem (구매오더 항목)
├─ id                     // 구매오더 항목 ID
├─ itemNo                 // 품목번호 (복합 PK의 일부분)
├─▶ **materialCode**         // 자재 코드 참조 (1:N)
├─ quantity               // 수량 
├─ deliveryDate           // 납품 예정일
├─▶ **Storage**              // 시설 정보 참조 (N:1)
└─▶ **PurchaseOrderHeader**  // 구매오더 헤더 참조 (N:1)

```

```scss
Material (자재 정보)
├─ id                   // 자재 ID
├─ name                 // 자재명
├─ baseUnit             // 기본 단위
└─ standardPrice        // 표준 단가
```

```scss
Facility (시설 정보)
├─ id                  // 회사 내부 시설 ID
├─ name                // 시설명
└─ address             // 주소
```

```scss
Storage (저장소 정보)
├─ id                  // 저장소 ID
├─▶ **Facility**          // 시설 정보 참조 (N:1)  Fetch.EAGER로 선언
└─ name                // 저장소 이름
```

- 시설(Facility) : 해당 물품의 회계 책임 소재를 지정하는 필드입니다. 어느 회사/시설로 재고가 입고 되었는지를 저장하기 위해 추가하였습니다.
- 저장소(Stoarge) : Facility 내부의 물리적 보관 장소로, 재고의 보관장소입니다. Facility 별로 물품을 여러 저장소가 있다고 판단하여 1:N의 의존 관계로 추가하였습니다.