# 실수, 구현 이해 부족

실수하거나 구현하는데 이해가 부족했던 부분을 정리한 문서입니다.

## 1. flush
> try - catch 문을 작성하는데, flush 처리를 하지 않아서 에러가 catch되지 않는 문제

```java
    @Transactional
    public void deleteVendors(List<IdRequest> vendorIdList) {
        try {
            vendorIdList.forEach(request -> vendorRepository.deleteById(request.getId()));
        } catch (DataIntegrityViolationException e) {
            throw new VendorException(ErrorCode.CONFLICT_RELATION_EXISTS);
        }
    }
```

위 코드에서 delete 처리 이후, flush를 처리하지 않았기 때문에 내부에서 `DataIntegrityViolationException`(연관된 데이터 삭제) 예외가
발생하지 않았습니다. 따라서 해당 예외를 catch할 수 있도록 다음과 같이 변경하였습니다.

```java
    @Transactional
public void deleteVendors(List<IdRequest> vendorIdList) {
    try {
        vendorIdList.forEach(request -> vendorRepository.deleteById(request.getId()));
        vendorRepository.flush();
    } catch (DataIntegrityViolationException e) {
        throw new VendorException(ErrorCode.CONFLICT_RELATION_EXISTS);
    }
}
```
