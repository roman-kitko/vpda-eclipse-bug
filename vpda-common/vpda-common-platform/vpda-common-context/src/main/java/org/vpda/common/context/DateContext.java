/**
 * View provider driven applications - java application framework for developing RIA
 * Copyright (C) 2009-2022 Roman Kitko, Slovakia
 *
 * Licensed under the GNU GENERAL PUBLIC LICENSE, Version 3.0 (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.gnu.org/licenses/gpl.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.vpda.common.context;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.IsoFields;
import java.util.Objects;

public final class DateContext implements Serializable {

    private DateContext(DateContextBuilder dateContextBuilder) {
        this.id = dateContextBuilder.getId();
        this.year = dateContextBuilder.getYear();
        this.month = dateContextBuilder.getMonth();
        this.quarter = dateContextBuilder.getQuater();
        this.yearWeek = dateContextBuilder.getYearWeek();
        this.dateContextGranularity = dateContextBuilder.getDateContextGranularity();
    }

    private static final long serialVersionUID = 5852654082448867948L;

    private final Long id;
    private final DateContextGranularity dateContextGranularity;
    private final Integer year;
    private final Integer month;
    private final Integer yearWeek;
    private final Integer quarter;

    public DateContextGranularity getDateContextGranularity() {
        return dateContextGranularity;
    }

    public Integer getYear() {
        return year;
    }

    public Integer getMonth() {
        return month;
    }

    public Integer getYearWeek() {
        return yearWeek;
    }

    public Integer getQuarter() {
        return quarter;
    }

    public static DateContext thisYear() {
        return new DateContextBuilder().setDateContextGranularity(DateContextGranularity.YEAR).setYear(LocalDate.now().getYear()).build();
    }

    @Override
    public String toString() {
        return "DateContext [dateContextGranularity=" + dateContextGranularity + ", year=" + year + ", month=" + month + ", yearWeek=" + yearWeek + ", quater=" + quarter + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateContextGranularity, month, quarter, year, yearWeek);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DateContext other = (DateContext) obj;
        return dateContextGranularity == other.dateContextGranularity && Objects.equals(month, other.month) && Objects.equals(quarter, other.quarter) && Objects.equals(year, other.year)
                && Objects.equals(yearWeek, other.yearWeek);
    }

    public int matchedFields(LocalDate date) {
        int currentYear = date.getYear();
        int currentMonth = date.getMonthValue();
        int currentQuarter = date.get(IsoFields.QUARTER_OF_YEAR);
        int currentWeek = date.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
        int match = 0;
        match = switch (dateContextGranularity) {
        case MONTH -> booleanToShort(Objects.equals(this.month, currentMonth)) + booleanToShort(Objects.equals(this.year, currentYear));
        case YEAR -> booleanToShort(Objects.equals(this.year, currentYear));
        case QUARTER -> booleanToShort(Objects.equals(this.year, currentYear)) + booleanToShort(Objects.equals(this.quarter, currentQuarter));
        case YEAR_WEEK -> booleanToShort(Objects.equals(this.year, currentYear)) + booleanToShort(Objects.equals(this.yearWeek, currentWeek));
        };
        return match;
    }

    private static int booleanToShort(boolean b) {
        return b ? 1 : 0;
    }

    public static DateContextBuilder createBuilder() {
        return new DateContextBuilder();
    }

    public DateContextBuilder createBuilderWithSameValues() {
        return new DateContextBuilder().setValues(this);
    }

    public Long getId() {
        return id;
    }

    public static final class DateContextBuilder implements org.vpda.common.util.Builder<DateContext> {

        private Long id;
        private Integer year;
        private Integer month;
        private Integer yearWeek;
        private Integer quater;
        private DateContextGranularity dateContextGranularity = DateContextGranularity.YEAR;

        @Override
        public DateContext build() {
            return new DateContext(this);
        }

        public Integer getYear() {
            return year;
        }

        public DateContextBuilder setYear(Integer year) {
            this.year = year;
            return this;
        }

        public Integer getMonth() {
            return month;
        }

        public DateContextBuilder setMonth(Integer month) {
            this.month = month;
            return this;
        }

        public Integer getYearWeek() {
            return yearWeek;
        }

        public DateContextBuilder setYearWeek(Integer yearWeek) {
            this.yearWeek = yearWeek;
            return this;
        }

        public Integer getQuater() {
            return quater;
        }

        public DateContextBuilder setQuater(Integer quater) {
            this.quater = quater;
            return this;
        }

        public DateContextGranularity getDateContextGranularity() {
            return dateContextGranularity;
        }

        public DateContextBuilder setDateContextGranularity(DateContextGranularity dateContextGranularity) {
            this.dateContextGranularity = dateContextGranularity;
            return this;
        }

        public DateContextBuilder setValues(DateContext ctx) {
            this.setId(ctx.getId());
            this.year = ctx.getYear();
            this.month = ctx.getMonth();
            this.quater = ctx.getQuarter();
            this.dateContextGranularity = ctx.getDateContextGranularity();
            this.yearWeek = ctx.getYearWeek();
            return this;
        }

        public Long getId() {
            return id;
        }

        public DateContextBuilder setId(Long id) {
            this.id = id;
            return this;
        }
    }
}
