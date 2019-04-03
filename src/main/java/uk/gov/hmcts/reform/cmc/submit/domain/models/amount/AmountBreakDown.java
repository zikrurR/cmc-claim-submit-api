package uk.gov.hmcts.reform.cmc.submit.domain.models.amount;

import lombok.Data;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
public class AmountBreakDown implements Amount {

    @Valid
    @NotNull
    private List<AmountRow> rows;

}
