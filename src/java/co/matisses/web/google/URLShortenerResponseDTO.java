package co.matisses.web.google;

import java.util.List;

/**
 *
 * @author dbotero
 */
public class URLShortenerResponseDTO {

    private String kind;
    private String id;
    private String longUrl;
    private Error error;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "URLShortenerResponseDTO{" + "kind=" + kind + ", id=" + id + ", longUrl=" + longUrl + ", error=" + error + '}';
    }

    public static class Error {

        private String code;
        private String message;
        private List<DetalleError> errors;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public List<DetalleError> getErrors() {
            return errors;
        }

        public void setErrors(List<DetalleError> errors) {
            this.errors = errors;
        }

        @Override
        public String toString() {
            return "Error{" + "code=" + code + ", message=" + message + ", errors=" + errors + '}';
        }

        public static class DetalleError {

            private String domain;
            private String reason;
            private String message;
            private String locationType;
            private String location;

            public String getDomain() {
                return domain;
            }

            public void setDomain(String domain) {
                this.domain = domain;
            }

            public String getReason() {
                return reason;
            }

            public void setReason(String reason) {
                this.reason = reason;
            }

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }

            public String getLocationType() {
                return locationType;
            }

            public void setLocationType(String locationType) {
                this.locationType = locationType;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            @Override
            public String toString() {
                return "DetalleError{" + "domain=" + domain + ", reason=" + reason + ", message=" + message + ", locationType=" + locationType + ", location=" + location + '}';
            }

        }
    }
}
