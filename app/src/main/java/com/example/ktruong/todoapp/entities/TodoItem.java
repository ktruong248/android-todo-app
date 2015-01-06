package com.example.ktruong.todoapp.entities;

public class TodoItem {
    private long id;
    private String body;
    private int priority;

    public TodoItem(long id, String body, int priority) {
        this.id = id;
        this.body = body;
        this.priority = priority;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TodoItem todoItem = (TodoItem) o;

        if (id != todoItem.id) return false;
        if (priority != todoItem.priority) return false;
        if (body != null ? !body.equals(todoItem.body) : todoItem.body != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (body != null ? body.hashCode() : 0);
        result = 31 * result + priority;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TodoItem{");
        sb.append("id=").append(id);
        sb.append(", body='").append(body).append('\'');
        sb.append(", priority=").append(priority);
        sb.append('}');
        return sb.toString();
    }
}
