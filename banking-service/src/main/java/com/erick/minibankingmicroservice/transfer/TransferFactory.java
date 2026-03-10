package com.erick.minibankingmicroservice.transfer;

import com.erick.minibankingmicroservice.enums.TransferType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransferFactory {

    private final BiFastTransfer biFastTransfer;
    private final OnlineTransfer onlineTransfer;
    private final RtgsTransfer rtgsTransfer;

    public TransferStrategy getStrategy(TransferType type) {

        return switch (type) {
            case BI_FAST -> biFastTransfer;
            case ONLINE -> onlineTransfer;
            case RTGS -> rtgsTransfer;
        };
    }
}
