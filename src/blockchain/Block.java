package blockchain;

import java.util.Date;

public class Block {
    public String hash;
    public String previousHash;
    private String data; // our data will be a simple message.
    private long timeStamp; // as number of milliseconds since 1/1/1970.
    private int nonce;

    // Block Constructor.
    public Block(String data, String previousHash) {
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();

        this.hash = calculateHash(); // Making sure we do this after we set the other values.
    }

    // Calculate new hash based on blocks contents
    public String calculateHash() {
        // System.out.println("[Block](calculateHash)-previousHash : " + previousHash);
        // System.out.println("[Block](calculateHash)-timeStamp : " +
        // Long.toString(timeStamp));
        // System.out.println("[Block](calculateHash)-data : " + data);
        String calculatedhash = StringUtil
                .applySha256(previousHash + Long.toString(timeStamp) + Integer.toString(nonce) + data);
        return calculatedhash;
    }

    public void mineBlock(int difficulty) {
        String target = new String(new char[difficulty]).replace('\0', '0'); // Create a string with difficulty * "0"
        System.out.println("[Block](mineBlock)-hash : " + hash.substring(0, difficulty) + " target : " + target + " = "
                + hash.substring(0, difficulty).equals(target));
        while (!hash.substring(0, difficulty).equals(target)) {
            nonce++;
            hash = calculateHash();
        }
        System.out.println("[Block] Mined!!! : " + hash);
    }

}
