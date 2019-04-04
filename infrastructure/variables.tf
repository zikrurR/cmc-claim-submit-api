variable "product" {
  type    = "string"
}

variable "raw_product" {
  default = "cmc" // jenkins-library overrides product for PRs and adds e.g. pr-118-cmc
}

variable "component" {
  type = "string"
}

variable "location_app" {
  type    = "string"
  default = "UK South"
}

variable "env" {
  type = "string"
}

variable "ilbIp" {}

variable "subscription" {}

variable "capacity" {
  default = "1"
}

variable "common_tags" {
  type = "map"
}
