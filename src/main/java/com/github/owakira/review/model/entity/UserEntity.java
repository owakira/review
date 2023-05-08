package com.github.owakira.review.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
        name = UserEntity.TABLE_NAME,
        schema = "public",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = UserEntity.EMAIL_UNIQUE_CONSTRAINT,
                        columnNames = UserEntity.EMAIL_COLUMN
                ),
                @UniqueConstraint(
                        name = UserEntity.USERNAME_UNIQUE_CONSTRAINT,
                        columnNames = UserEntity.USERNAME_COLUMN
                ),
        }
)
@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserEntity {
    private static final int EMAIL_COLUMN_LENGTH = 256;
    private static final int USERNAME_COLUMN_LENGTH = 32;
    private static final int PASSWORD_COLUMN_LENGTH = 60;

    public static final String TABLE_NAME = "usr";

    public static final String ID_COLUMN = "id";
    public static final String EMAIL_COLUMN = "email";
    public static final String USERNAME_COLUMN = "username";
    public static final String PASSWORD_COLUMN = "password";
    public static final String CREATED_AT_COLUMN = "created_at";

    public static final String EMAIL_UNIQUE_CONSTRAINT = "uk_email";
    public static final String USERNAME_UNIQUE_CONSTRAINT = "uk_username";

    @Id
    @Column(name = ID_COLUMN)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = EMAIL_COLUMN, nullable = false, length = EMAIL_COLUMN_LENGTH)
    @ToString.Exclude
    private String email;

    @Column(name = USERNAME_COLUMN, nullable = false, length = USERNAME_COLUMN_LENGTH)
    private String username;

    @Column(name = PASSWORD_COLUMN, nullable = false, length = PASSWORD_COLUMN_LENGTH)
    @ToString.Exclude
    private String password;

    @Column(name = CREATED_AT_COLUMN)
    @CreationTimestamp
    private LocalDateTime createdAt;
}
