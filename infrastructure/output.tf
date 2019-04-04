output "microserviceName" {
  value = "${var.component}"
}

output "s2s_url" {
  value = "${local.s2s_url}"
}

output "s2s_secret" {
  value = "${data.azurerm_key_vault_secret.s2s_secret.value}"
}

output "ccd_url" {
  value = "${local.ccd_url}"
}