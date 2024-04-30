package at.loremipsum.books.dto;

/**
 * Data Transfer Object representing a book's compensation.
 * This class simplifies client-server communication by carrying data between processes.
 */
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

    /**
     * Inner class to encapsulate the compensation amounts and details.
     */
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

        /**
         * Inner class to encapsulate specific compensation factors such as base, age, page, and language factors.
         */
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
