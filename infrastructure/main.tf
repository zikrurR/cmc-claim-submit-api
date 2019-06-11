provider "azurerm" {}

locals {
  ase_name       = "${data.terraform_remote_state.core_apps_compute.ase_name[0]}"
  s2s_url        = "http://rpe-service-auth-provider-${var.env}.service.${local.ase_name}.internal"
  ccd_url        = "http://ccd-data-store-api-${var.env}.service.${local.ase_name}.internal"
}

module "cmc-claim-submit-api" {
  source              = "git@github.com:hmcts/moj-module-webapp?ref=master"
  product             = "${var.product}-${var.component}"
  location            = "${var.location_app}"
  env                 = "${var.env}"
  ilbIp               = "${var.ilbIp}"
  subscription        = "${var.subscription}"
  capacity            = "${var.capacity}"
  common_tags         = "${var.common_tags}"
  asp_name            = "cmc-${var.env}"
  asp_rg              = "cmc-${var.env}"

  app_settings = {
    LOGBACK_REQUIRE_ALERT_LEVEL = "false"
    LOGBACK_REQUIRE_ERROR_CODE  = "false"
    IDAM_S2S_URL                = "${local.s2s_url}"
    IDAM_S2S_KEY                = "${data.azurerm_key_vault_secret.s2s_secret.value}"
    CORE_CASE_DATA_API_URL      = "${local.ccd_url}"
  }
}

data "azurerm_key_vault" "s2s_key_vault" {
  name = "s2s-${var.env}"
  resource_group_name = "rpe-service-auth-provider-${var.env}"
}

data "azurerm_key_vault_secret" "s2s_secret" {
  name = "microservicekey-cmc-claim-external-api"
  key_vault_id = "${data.azurerm_key_vault.s2s_key_vault.id}"
}