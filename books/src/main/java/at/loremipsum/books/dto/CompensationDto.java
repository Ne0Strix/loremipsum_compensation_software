package at.loremipsum.books.dto;

public class CompensationDto {
    private String isbn;
    private String title;
    private Compensation Compensation;

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public CompensationDto.Compensation getCompensation() {
        return Compensation;
    }

    public void setCompensation(CompensationDto.Compensation compensation) {
        Compensation = compensation;
    }

    public static class Compensation {
        private float amount;
        private String currency;
        private CompensationDetails details;

        public float getAmount() {
            return amount;
        }

        public void setAmount(float amount) {
            this.amount = amount;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public CompensationDetails getDetails() {
            return details;
        }

        public void setDetails(CompensationDetails details) {
            this.details = details;
        }

        public static class CompensationDetails {
            private float baseCompensation;
            private float ageCompensation;
            private float pageCompensationFactor;
            private float languageCompensationFactor;

            public float getBaseCompensation() {
                return baseCompensation;
            }

            public void setBaseCompensation(float baseCompensation) {
                this.baseCompensation = baseCompensation;
            }

            public float getAgeCompensation() {
                return ageCompensation;
            }

            public void setAgeCompensation(float ageCompensation) {
                this.ageCompensation = ageCompensation;
            }

            public float getPageCompensationFactor() {
                return pageCompensationFactor;
            }

            public void setPageCompensationFactor(float pageCompensationFactor) {
                this.pageCompensationFactor = pageCompensationFactor;
            }

            public float getLanguageCompensationFactor() {
                return languageCompensationFactor;
            }

            public void setLanguageCompensationFactor(float languageCompensationFactor) {
                this.languageCompensationFactor = languageCompensationFactor;
            }
        }
    }
}
