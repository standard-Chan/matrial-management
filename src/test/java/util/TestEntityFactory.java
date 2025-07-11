package util;

import com.springframework.mm.domain.*;
import com.springframework.mm.domain.purchaseOrder.PurchaseOrderHeader;
import com.springframework.mm.domain.purchaseOrder.PurchaseOrderItem;
import com.springframework.mm.domain.vendor.*;
import com.springframework.mm.enums.*;

import java.time.LocalDate;

public class TestEntityFactory {

    public static Vendor createVendor(String name) {
        return Vendor.builder()
                .name(name)
                .countryCode("KR")
                .address("서울 강남구")
                .businessRegistrationNo("123-45-67890")
                .personalId("900101-1234567")
                .vendorGroupCode(VendorGroupCode.DOMESTIC)
                .build();
    }

    public static VendorCompany createVendorCompany(Vendor vendor, String companyCode) {
        return VendorCompany.builder()
                .vendor(vendor)
                .companyCode(companyCode)
                .accountCode(AccountCode.PAYABLE_DOMESTIC)
                .paymentTermCode(PaymentTermCode.M001)
                .build();
    }

    public static VendorPurchasing createVendorPurchasing(Vendor vendor) {
        return VendorPurchasing.builder()
                .vendor(vendor)
                .purchasingOrgCode("1000")
                .purchasingGroupCode("110")
                .currency("KRW")
                .taxCode(TaxCode.V1)
                .build();
    }

    public static Facility createFacility(String name) {
        return Facility.builder()
                .name(name)
                .address("경기도 수원시")
                .build();
    }

    public static Storage createStorage(String name, Facility facility) {
        return Storage.builder()
                .name(name)
                .facility(facility)
                .build();
    }

    public static Material createMaterial(String name, QuantityUnit unit, int price) {
        return Material.builder()
                .name(name)
                .baseUnit(unit)
                .price(price)
                .build();
    }

    public static PurchaseOrderHeader createPurchaseOrderHeader(VendorCompany vendorCompany) {
        return PurchaseOrderHeader.builder()
                .vendorCompany(vendorCompany)
                .orderDate(LocalDate.now())
                .build();
    }

    public static PurchaseOrderItem createPurchaseOrderItem(
            Material material,
            Storage storage,
            PurchaseOrderHeader header,
            Long quantity,
            LocalDate deliveryDate
    ) {
        return PurchaseOrderItem.builder()
                .material(material)
                .storage(storage)
                .purchaseOrderHeader(header)
                .quantity(quantity)
                .deliveryDate(deliveryDate)
                .build();
    }
}

