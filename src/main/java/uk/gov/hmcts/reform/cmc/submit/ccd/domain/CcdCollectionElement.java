package uk.gov.hmcts.reform.cmc.submit.ccd.domain;

import lombok.Data;

@Data
public class CcdCollectionElement<T> {
    private String id;
    private T value;
}
