package org.authservice.Response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response {
    ResponseStatus status;
    Object data;
}
