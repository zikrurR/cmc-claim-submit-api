package uk.gov.hmcts.reform.cmc.submit.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uk.gov.hmcts.reform.cmc.submit.domain.models.Claim;
import uk.gov.hmcts.reform.cmc.submit.domain.models.ClaimInput;
import uk.gov.hmcts.reform.cmc.submit.domain.models.ClaimOutput;
import uk.gov.hmcts.reform.cmc.submit.exception.ApplicationException;
import uk.gov.hmcts.reform.cmc.submit.services.ClaimService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Api
@RestController
@RequestMapping("/claim")
public class ClaimController {

    private final ClaimService claimService;

    @Autowired
    public ClaimController(ClaimService claimService) {
        this.claimService = claimService;
    }

    @GetMapping("/{externalIdentifier}")
    @ApiOperation("Fetch claim for given external id or reference")
    public Claim getClaim(
            @PathVariable String externalIdentifier) throws ApplicationException {

        return claimService.getClaim(externalIdentifier);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("Creates a new claim")
    public ClaimOutput save(
            @Valid @NotNull @RequestBody ClaimInput claimData) throws ApplicationException {

        return claimService.createNewCase(claimData);
    }

}
