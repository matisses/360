package co.matisses.web.p2p;

/**
 *
 * @author dbotero
 */
public class DatosPagoPlaceToPayDTO {

    private Buyer buyer;
    private String locale;
    private String userAgent;
    private Payment payment;
    private Auth auth;
    private String expiration;
    private String returnUrl;
    private String ipAddress;

    public DatosPagoPlaceToPayDTO() {
        this.locale = "es_CO";
    }

    public Auth getAuth() {
        return auth;
    }

    public void setAuth(Auth auth) {
        this.auth = auth;
    }

    public void setAuth(String login, String seed, String nonce, String tranKey) {
        this.auth = new Auth(login, seed, nonce, tranKey);
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public class Buyer {

        private String documentType;
        private String document;
        private String name;
        private String surname;
        private String email;
        private String mobile;
        private Address address;

        public Address getAddress() {
            return address;
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getDocumentType() {
            return documentType;
        }

        public void setDocumentType(String documentType) {
            this.documentType = documentType;
        }

        public String getDocument() {
            return document;
        }

        public void setDocument(String document) {
            this.document = document;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSurname() {
            return surname;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public class Address {

            private String street;
            private String city;
            private String country;

            public Address() {
                this.country = "CO";
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public String getStreet() {
                return street;
            }

            public void setStreet(String street) {
                this.street = street;
            }
        }
    }

    public class Payment {

        private String allowPartial;
        private String reference;
        private String description;// 'Compra lista regalos',
        private Amount amount;

        public Payment() {
            this.allowPartial = "false";
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getAllowPartial() {
            return allowPartial;
        }

        public void setAllowPartial(String allowPartial) {
            this.allowPartial = allowPartial;
        }

        public String getReference() {
            return reference;
        }

        public void setReference(String reference) {
            this.reference = reference;
        }

        public Amount getAmount() {
            return amount;
        }

        public void setAmount(Amount amount) {
            this.amount = amount;
        }

        public class Amount {

            private String currency;
            private String total;
            private Taxes taxes;

            public Amount() {
                this.currency = "COP";
            }

            public String getCurrency() {
                return currency;
            }

            public void setCurrency(String currency) {
                this.currency = currency;
            }

            public String getTotal() {
                return total;
            }

            public void setTotal(String total) {
                this.total = total;
            }

            public Taxes getTaxes() {
                return taxes;
            }

            public void setTaxes(Taxes taxes) {
                this.taxes = taxes;
            }

            public class Taxes {

                private String kind;
                private String amount;

                public Taxes() {
                    this.kind = "valueAddedTax";
                }

                public String getAmount() {
                    return amount;
                }

                public void setAmount(String amount) {
                    this.amount = amount;
                }

                public String getKind() {
                    return kind;
                }

                public void setKind(String kind) {
                    this.kind = kind;
                }
            }
        }
    }

    public class Auth {

        private String login;
        private String seed;
        private String nonce;
        private String tranKey;

        public Auth() {
        }

        public Auth(String login, String seed, String nonce, String tranKey) {
            this.login = login;
            this.seed = seed;
            this.nonce = nonce;
            this.tranKey = tranKey;
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getNonce() {
            return nonce;
        }

        public void setNonce(String nonce) {
            this.nonce = nonce;
        }

        public String getSeed() {
            return seed;
        }

        public void setSeed(String seed) {
            this.seed = seed;
        }

        public String getTranKey() {
            return tranKey;
        }

        public void setTranKey(String tranKey) {
            this.tranKey = tranKey;
        }
    }
}
