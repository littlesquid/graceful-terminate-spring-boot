package com.samuraiiway.gracefulterminate.receiver.message;

import lombok.Data;

@Data
public class CustomerLoginMessage {
    private String id;
    private String name;
    private String status;
}
