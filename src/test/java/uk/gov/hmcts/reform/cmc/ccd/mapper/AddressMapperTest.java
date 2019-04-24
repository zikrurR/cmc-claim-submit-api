package uk.gov.hmcts.reform.cmc.ccd.mapper;

import org.junit.jupiter.api.Test;

import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdAddress;
import uk.gov.hmcts.reform.cmc.submit.domain.models.common.Address;
import uk.gov.hmcts.reform.cmc.submit.domain.samples.SampleAddress;
import uk.gov.hmcts.reform.cmc.submit.mapper.AddressMapper;

import static uk.gov.hmcts.reform.cmc.ccd.assertion.Assertions.assertThat;

public class AddressMapperTest {

    private AddressMapper mapper = new AddressMapper();

    @Test
    public void shouldMapAddressToCcd() {
        //given
        Address address = SampleAddress.validDefaults();

        //when
        CcdAddress ccdAddress = mapper.to(address);

        //then
        assertThat(ccdAddress).isEqualTo(address);

    }

    @Test
    public void shouldMapAddressToCmc() {
        //given
        CcdAddress ccdAddress = new CcdAddress();
        ccdAddress.setAddressLine1("line1");
        ccdAddress.setAddressLine3("line2");
        ccdAddress.setAddressLine2("line3");
        ccdAddress.setPostTown("city");
        ccdAddress.setPostCode("postcode");

        //when
        Address address = mapper.from(ccdAddress);

        //then
        assertThat(address).isEqualTo(ccdAddress);
    }
}
