package com.onito.emailservices;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServices {
@Autowired
private JavaMailSender mailsender;
@Value("${spring.mail.username}")
private String username;
public void sendEmailWithAttachment(String to,List<List<Integer>> ticketData) throws MessagingException, IOException {
	File filetrack=generateTicketFile(ticketData);
	MimeMessage message=mailsender.createMimeMessage();
	MimeMessageHelper helper=new MimeMessageHelper(message,true);
	try {
		helper.setFrom(username);
		helper.setTo(to);
		helper.setSubject("Generated Tambola Ticket ");
		helper.setText("Enjoy your game ");
		FileSystemResource file=new FileSystemResource(filetrack);
		helper.addAttachment( file.getFilename(),file);
		mailsender.send(message);
	
	}
	finally {
		filetrack.delete();
	}
	}
private File generateTicketFile(List<List<Integer>> ticketData) throws IOException {
    File ticketFile = File.createTempFile("Tambola_ticket", ".txt");
    try (FileWriter writer = new FileWriter(ticketFile)) {
        int rowCount = 0; // Counter for rows processed
        for (List<Integer> ticketRow : ticketData) {
            for (int i = 0; i < ticketRow.size(); i++) {
                Integer number = ticketRow.get(i);
                writer.write(formatNumber(number));

                // Add tab unless it's the last column
                if (i < ticketRow.size() - 1) {
                    writer.write("\t");
                }
            }
            writer.write("\n");
            
            rowCount++;
           
            if (rowCount % 3 == 0 && rowCount != ticketData.size()) {
                writer.write("______________________________________________________\n");
            }
        }
        return ticketFile;
    }
    
}
private String formatNumber(Integer number) {
    if (number == null || number == 0) {
        return "00";
    } else {
        return String.format("%2d", number); 
    }
}


}
