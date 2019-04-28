package uk.gov.hmcts.reform.cmc.ccd.mapper;

import org.junit.jupiter.api.Test;

import uk.gov.hmcts.reform.cmc.submit.domain.models.common.Address;
import uk.gov.hmcts.reform.cmc.submit.domain.samples.SampleAddress;
import uk.gov.hmcts.reform.cmc.submit.mapper.AddressMapper;

import static org.assertj.core.api.Assertions.assertThat;

public class AddressMapperTest {

    private AddressMapper mapper = new AddressMapper();

    @Test
    public void shouldMapAddressToAddressBack() {
        //given
        Address address = SampleAddress.validDefaults();

        //when
        Address addressBack = mapper.from(mapper.to(address));

        //then
        assertThat(addressBack).isEqualTo(address);

    }

}
