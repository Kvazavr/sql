package ru.netology.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class AuthCode {
private String id;
private String user_id;
private String code;
private Timestamp created;
}
