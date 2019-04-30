package uk.gov.hmcts.reform.cmc.submit.merger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdApplicant;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdPartyType;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.builders.CcdApplicantBuilder;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.builders.CcdCaseBuilder;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.builders.CcdCollectionElementBuilder;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.builders.CcdPartyBuilder;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.builders.CcdTelephoneBuilder;
import uk.gov.hmcts.reform.cmc.submit.domain.models.ClaimInput;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.Company;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.Individual;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.Organisation;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.Party;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.SoleTrader;
import uk.gov.hmcts.reform.cmc.submit.domain.models.common.ContactDetails;
import uk.gov.hmcts.reform.cmc.submit.domain.models.common.Representative;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
class MergeCaseDataApplicants implements MergeCaseDataDecorator {

    private final AddressBuilderConverter addressBuilderConverter;

    @Autowired
    public MergeCaseDataApplicants(AddressBuilderConverter addressBuilderConverter) {
        this.addressBuilderConverter = addressBuilderConverter;
    }

    @Override
    public void merge(CcdCaseBuilder ccdCaseBuilder, ClaimInput claim) {
        ccdCaseBuilder.applicants(to(claim.getClaimants()));
    }

    private List<CcdCollectionElementBuilder<CcdApplicant>> to(List<? extends Party> parties) {
        if (parties == null) {
            return new ArrayList<>();
        }

        return parties.stream()
                .filter(Objects::nonNull)
                .map(this::to)
                .collect(Collectors.toList());
    }

    private CcdCollectionElementBuilder<CcdApplicant> to(Party party) {
        CcdApplicantBuilder builder = CcdApplicantBuilder.builder();

        CcdPartyBuilder partyDetailBuilder = CcdPartyBuilder.builder();
        CcdTelephoneBuilder telephone = CcdTelephoneBuilder.builder()
                                                        .telephoneNumber(party.getMobilePhone());
        partyDetailBuilder.telephoneNumber(telephone);

        partyDetailBuilder.primaryAddress(addressBuilderConverter.to(party.getAddress()));
        partyDetailBuilder.correspondenceAddress(addressBuilderConverter.to(party.getCorrespondenceAddress()));

        builder.partyName(party.getName());

        representative(party.getRepresentative(), builder);
        builder.partyDetail(partyDetailBuilder);


        if (party instanceof Individual) {
            partyDetailBuilder.type(CcdPartyType.INDIVIDUAL);
            partyDetailBuilder.dateOfBirth(((Individual)party).getDateOfBirth());
        } else if (party instanceof Company) {
            partyDetailBuilder.type(CcdPartyType.COMPANY);
            partyDetailBuilder.contactPerson(((Company)party).getContactPerson());
        } else if (party instanceof Organisation) {
            partyDetailBuilder.type(CcdPartyType.ORGANISATION);
            partyDetailBuilder.contactPerson(((Organisation)party).getContactPerson());
            partyDetailBuilder.companiesHouseNumber(((Organisation)party).getCompaniesHouseNumber());
        } else if (party instanceof SoleTrader) {
            partyDetailBuilder.type(CcdPartyType.SOLE_TRADER);
            partyDetailBuilder.title(((SoleTrader)party).getTitle());
            partyDetailBuilder.businessName(((SoleTrader)party).getBusinessName());
        }

        return CcdCollectionElementBuilder.<CcdApplicant>builder()
                                            .value(builder)
                                            .id(party.getId());
    }

    private void representative(Representative representative, CcdApplicantBuilder builder) {
        if (representative == null) {
            return;
        }
        ContactDetails contactDetails = representative.getOrganisationContactDetails();

        builder.representativeOrganisationEmail(contactDetails.getEmail());
        builder.representativeOrganisationPhone(contactDetails.getPhone());
        builder.representativeOrganisationDxAddress(contactDetails.getDxAddress());

        builder.representativeOrganisationName(representative.getOrganisationName());
        builder.representativeOrganisationAddress(addressBuilderConverter.to(representative.getOrganisationAddress()));
    }
}
