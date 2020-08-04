package PEJ;

import PEJ.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PolicyHandler{

    @Autowired
    DeliveryRepository deliveryRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverOrdered_Delivery(@Payload Ordered ordered){

        if(ordered.isMe()){
            Delivery delivery = new Delivery();
            delivery.setOrderId(ordered.getOrderId());
            delivery.setDeliveryStatus("SHIPPED");

            delivery.setPrdId(ordered.getPrdId());
            delivery.setPrdNm(ordered.getPrdNm());
            delivery.setPrdPrice(ordered.getPrdPrice());
            delivery.setPrdQty(ordered.getPrdQty());

            deliveryRepository.save(delivery);
            System.out.println("##### listener Delivery : " + ordered.toJson());
        }

    }

}
