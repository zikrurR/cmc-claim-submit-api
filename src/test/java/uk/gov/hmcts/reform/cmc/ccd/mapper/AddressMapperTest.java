package uk.gov.hmcts.reform.cmc.ccd.mapper;

import org.junit.Test;

import uk.gov.hmcts.cmc.ccd.domain.CCDAddress;
import uk.gov.hmcts.cmc.domain.models.common.Address;
import uk.gov.hmcts.reform.cmc.ccd.builders.SampleAddress;

import static uk.gov.hmcts.reform.cmc.ccd.assertion.Assertions.assertThat;

public class AddressMapperTest {

    private AddressMapper mapper = new AddressMapper();

    @Test
    public void shouldMapAddressToCcd() {
        //given
        Address address = SampleAddress.builder().build();

        //when
        CCDAddress ccdAddress = mapper.to(address);

        //then
        assertThat(ccdAddress).isEqualTo(address);

    }

    @Test
    public void shouldMapAddressToCmc() {
        //given
        CCDAddress ccdAddress = CCDAddress.builder()
            .addressLine1("line1")
            .addressLine3("line2")
            .addressLine2("line3")
            .postTown("city")
            .postCode("postcode")
            .build();

        //when
        Address address = mapper.from(ccdAddress);

        //then
        assertThat(address).isEqualTo(ccdAddress);
    }
}
