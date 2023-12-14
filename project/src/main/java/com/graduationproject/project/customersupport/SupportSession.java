package com.graduationproject.project.customersupport;

import java.util.List;

import com.graduationproject.project.customersupport.chatmessage.Message;
import com.graduationproject.project.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name = "support_sessions")
public class SupportSession {
   @Setter
   private boolean closed;
   @OneToMany(mappedBy = "session",targetEntity = Message.class)
   private List<Message> messages;
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;
   @ManyToOne(fetch = FetchType.LAZY,targetEntity = User.class)
   private User user;

   public void addMessage(Message message){
      this.messages.add(message);
   }
}
