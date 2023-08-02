package com.example.be_project.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Table(name ="Role")
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role {
    @Id
    @Column(name= "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Column(name= "user_id")
    long userId;

    @Column(name = "proposal_pay_receipt")
    boolean proposalPayReceipt; // đề xuất thu chi
    @Column(name = "receipt_action")
    boolean receiptAction; // thu hđ
    @Column(name = "pay_action")
    boolean payAction; // chi hđ
    @Column(name = "ads_task")
    boolean adsTask;
    @Column(name = "media_task")
    boolean mediaTask;
    boolean report;
    @Column(name = "list_wallet")
    boolean listWallet;

}
