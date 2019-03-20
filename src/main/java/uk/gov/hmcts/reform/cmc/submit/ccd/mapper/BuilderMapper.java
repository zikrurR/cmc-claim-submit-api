package uk.gov.hmcts.reform.cmc.submit.ccd.mapper;

public interface BuilderMapper<K, V, B> {

    void to(V v, B builder);

    V from(K k);
}
