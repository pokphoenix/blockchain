package blockchain;

import java.security.PublicKey;

public class TransactionOutput {
    public String id;
    public PublicKey reciepient; // also known as the new owner of these coins.
    public float value; // the amount of coins they own
    public String parentTransactionId; // the id of the transaction this output was created in
    public String name;

    // Constructor
    public TransactionOutput(PublicKey reciepient, float value, String parentTransactionId, String reciepientName) {
        this.reciepient = reciepient;
        this.name = reciepientName;
        this.value = value;
        this.parentTransactionId = parentTransactionId;
        this.id = StringUtil
                .applySha256(StringUtil.getStringFromKey(reciepient) + Float.toString(value) + parentTransactionId);
    }

    // Check if coin belongs to you
    public boolean isMine(PublicKey publicKey) {
        return (publicKey == reciepient);
    }

    public float getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
