package com.cyj.hb.model;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Embeddable
public class StayReservedDateKey implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long stay_id;
    private LocalDate date;

    public StayReservedDateKey() {
    }

    public StayReservedDateKey(Long stay_id, LocalDate date) {
        this.stay_id = stay_id;
        this.date = date;
    }

    public Long getStay_id() {
        return stay_id;
    }

    public void setStay_id(Long stay_id) {
        this.stay_id = stay_id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public int hashCode() {
        return Objects.hash(stay_id, date);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        StayReservedDateKey that = (StayReservedDateKey) obj;
        return stay_id.equals(that.stay_id) && date.equals(that.date);
    }
}
