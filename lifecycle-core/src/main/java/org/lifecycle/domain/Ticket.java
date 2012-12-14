package org.lifecycle.domain;

public class Ticket {

    private Long id;
    private String title;

    public Ticket(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

}
