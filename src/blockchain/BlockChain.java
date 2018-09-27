package blockchain;

import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.GsonBuilder;

public class BlockChain {

    public static ArrayList<Block> blockchain = new ArrayList<Block>();
    public static HashMap<String, TransactionOutput> UTXOs = new HashMap<String, TransactionOutput>(); // list of all
                                                                                                       // unspent
                                                                                                       // transactions.
    public static int difficulty = 3;
    public static float minimumTransaction = 0.1f;
    public static Wallet walletA;
    public static Wallet walletB;
    public static Wallet walletC;
    public static Transaction genesisTransaction;

    public static void main(String[] args) {

        // blockchain.add(new Block("Hi im the first block", "0"));
        // System.out.println("Trying to Mine block 1... ");
        // blockchain.get(0).mineBlock(difficulty);
        //
        // blockchain.add(new Block("Yo im the second block",
        // blockchain.get(blockchain.size() - 1).hash));
        // System.out.println("Trying to Mine block 2... ");
        // blockchain.get(1).mineBlock(difficulty);
        //
        // blockchain.add(new Block("Hey im the third block",
        // blockchain.get(blockchain.size() - 1).hash));
        // System.out.println("Trying to Mine block 3... ");
        // blockchain.get(2).mineBlock(difficulty);
        //
        // System.out.println("\nBlockchain is Valid: " + isChainValid());
        //
        // String blockchainJson = new
        // GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
        // System.out.println("\nThe block chain: ");
        // System.out.println(blockchainJson);
        // Setup Bouncey castle as a Security Provider
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        // Create the new wallets
        walletA = new Wallet("wallet A");
        walletB = new Wallet("wallet B");
        walletC = new Wallet("wallet C");
        Wallet coinbase = new Wallet("SYSTEM");
        // create genesis transaction, which sends 100 NoobCoin to walletA:
        genesisTransaction = new Transaction(coinbase.publicKey, walletA.publicKey, 100f, null, coinbase.getName(),
                walletA.getName());
        genesisTransaction.generateSignature(coinbase.privateKey); // manually sign the genesis transaction
        genesisTransaction.transactionId = "0"; // manually set the transaction id
        genesisTransaction.outputs.add(new TransactionOutput(genesisTransaction.reciepient, genesisTransaction.value,
                genesisTransaction.transactionId, walletA.getName())); // manually add the Transactions Output
        UTXOs.put(genesisTransaction.outputs.get(0).id, genesisTransaction.outputs.get(0)); // its important to store
                                                                                            // our first transaction in
                                                                                            // the UTXOs list.

        System.out.println("Creating and Mining Genesis block... ");
        Block genesis = new Block("0");
        genesis.addTransaction(genesisTransaction);
        addBlock(genesis);

        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(UTXOs);
        System.out.println("\nThe block chain: ");
        System.out.println(blockchainJson);

        // testing
        Block block1 = new Block(genesis.hash);
        System.out.println("\nWalletA's balance is: " + walletA.getBalance());
        System.out.println("\nWalletA is Attempting to send funds (40) to WalletB...");
        block1.addTransaction(walletA.sendFunds(walletB.publicKey, 40f, walletB.getName()));
        addBlock(block1);
        System.out.println("\nWalletA's balance is: " + walletA.getBalance());
        System.out.println("WalletB's balance is: " + walletB.getBalance());

        Block block2 = new Block(block1.hash);
        System.out.println("\nWalletA Attempting to send more funds (1000) than it has...");
        block2.addTransaction(walletA.sendFunds(walletC.publicKey, 1000f, walletC.getName()));
        addBlock(block2);
        System.out.println("\nWalletA's balance is: " + walletA.getBalance());
        System.out.println("WalletC's balance is: " + walletC.getBalance());
        //
        // String blockchainJson3 = new
        // GsonBuilder().setPrettyPrinting().create().toJson(UTXOs);
        // System.out.println("\nThe block chain 3: ");
        // System.out.println(blockchainJson3);
        //
        Block block3 = new Block(block2.hash);
        System.out.println("\nWalletA is Attempting to send funds (10) to WalletB...");
        block3.addTransaction(walletA.sendFunds(walletB.publicKey, 10f, walletB.getName()));
        addBlock(block3);
        System.out.println("\nWalletA's balance is: " + walletA.getBalance());
        System.out.println("WalletB's balance is: " + walletB.getBalance());
        //
        // String blockchainJson4 = new
        // GsonBuilder().setPrettyPrinting().create().toJson(UTXOs);
        // System.out.println("\nThe block chain 4: ");
        // System.out.println(blockchainJson4);
        //
        Block block4 = new Block(block3.hash);
        System.out.println("\nWalletB is Attempting to send funds (5) to WalletA...");
        block4.addTransaction(walletB.sendFunds(walletA.publicKey, 5f, walletA.getName()));
        addBlock(block4);
        System.out.println("\nWalletA's balance is: " + walletA.getBalance());
        System.out.println("WalletB's balance is: " + walletB.getBalance());
        //
        // String blockchainJson5 = new
        // GsonBuilder().setPrettyPrinting().create().toJson(UTXOs);
        // System.out.println("\nThe block chain 5: ");
        // System.out.println(blockchainJson5);
        //
        Block block5 = new Block(block4.hash);
        System.out.println("\nWalletA is Attempting to send funds (20) to WalletC...");
        block5.addTransaction(walletA.sendFunds(walletC.publicKey, 20f, walletC.getName()));
        addBlock(block5);
        System.out.println("\nWalletA's balance is: " + walletA.getBalance());
        System.out.println("WalletC's balance is: " + walletC.getBalance());
        //
        // String blockchainJson6 = new
        // GsonBuilder().setPrettyPrinting().create().toJson(UTXOs);
        // System.out.println("\nThe block chain 6: ");
        // System.out.println(blockchainJson6);
        //

        //
        Block block6 = new Block(block5.hash);
        System.out.println("\nWalletA is Attempting to send funds (70) to WalletC...");
        block6.addTransaction(walletA.sendFunds(walletC.publicKey, 70f, walletC.getName()));
        addBlock(block6);

        System.out.println("\nWalletA's balance is: " + walletA.getBalance());
        System.out.println("WalletC's balance is: " + walletC.getBalance());

        String blockchainJson7 = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
        System.out.println("\nThe block chain 7: ");
        System.out.println(blockchainJson7);
        System.out.println("");
        int index = 1;
        for (Block block : blockchain) {

            System.out.println("[" + index + "] nonce : " + block.getNonce());

            ArrayList bTran = block.getTransaction();

            int loop = bTran.size();

            if (loop <= 0) {
                System.out.println(" transaction : false");
            } else {

                for (int i = 0; i < loop; i++) {
                    Transaction tran = (Transaction) bTran.get(i);
                    System.out.println(" \" " + tran.getSenderName() + " \" send  funds( " + tran.getValue()
                            + " ) to \" " + tran.getReciepientName() + " \" ");
                    ArrayList input = tran.getInput();
                    if (input != null) {
                        int inputSizes = input.size();
                        if (inputSizes <= 0) {
                            System.out.println(" input : []");
                        } else {
                            System.out.println(" input : ");
                            int inPutIndex = 1;
                            for (int inputSize = 0; inputSize < inputSizes; inputSize++) {
                                TransactionInput inputData = (TransactionInput) input.get(inputSize);
                                TransactionOutput inputUTXO = inputData.getTransactionInput();
                                System.out.println(
                                        inPutIndex + " ). " + inputUTXO.getName() + " value : " + inputUTXO.getValue());
                                inPutIndex++;
                            }
                        }
                    }

                    ArrayList output = tran.getOutput();
                    if (output != null) {
                        int outputSizes = output.size();
                        if (outputSizes <= 0) {
                            System.out.println(" output : []");
                        } else {
                            System.out.println(" output : ");
                            int outPutIndex = 1;
                            for (int outputSize = 0; outputSize < outputSizes; outputSize++) {
                                TransactionOutput UTXOdata = (TransactionOutput) output.get(outputSize);
                                System.out.println(
                                        outPutIndex + " ). " + UTXOdata.getName() + " value : " + UTXOdata.getValue());
                                outPutIndex++;
                            }
                        }
                    }

                }
            }

            index++;
            System.out.println("===========================");
        }

        // Collections.shuffle(blockchain);

        isChainValid();

    }

    public static Boolean isChainValid() {
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');
        HashMap<String, TransactionOutput> tempUTXOs = new HashMap<String, TransactionOutput>(); // a temporary working
                                                                                                 // list of unspent
                                                                                                 // transactions at a
                                                                                                 // given block state.
        tempUTXOs.put(genesisTransaction.outputs.get(0).id, genesisTransaction.outputs.get(0));

        // loop through blockchain to check hashes:
        for (int i = 1; i < blockchain.size(); i++) {
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i - 1);
            // compare registered hash and calculated hash:
            if (!currentBlock.hash.equals(currentBlock.calculateHash())) {
                System.out.println("Current Hashes not equal");
                return false;
            }
            // compare previous hash and registered previous hash
            if (!previousBlock.hash.equals(currentBlock.previousHash)) {
                System.out.println("Previous Hashes not equal");
                return false;
            }
            // check if hash is solved
            if (!currentBlock.hash.substring(0, difficulty).equals(hashTarget)) {
                System.out.println("This block hasn't been mined");
                return false;
            }

            // loop thru blockchains transactions:
            TransactionOutput tempOutput;
            for (int t = 0; t < currentBlock.transactions.size(); t++) {
                Transaction currentTransaction = currentBlock.transactions.get(t);

                if (!currentTransaction.verifiySignature()) {
                    System.out.println("#Signature on Transaction(" + t + ") is Invalid");
                    return false;
                }
                if (currentTransaction.getInputsValue() != currentTransaction.getOutputsValue()) {
                    System.out.println("#Inputs are note equal to outputs on Transaction(" + t + ")");
                    return false;
                }

                for (TransactionInput input : currentTransaction.inputs) {
                    tempOutput = tempUTXOs.get(input.transactionOutputId);

                    if (tempOutput == null) {
                        System.out.println("#Referenced input on Transaction(" + t + ") is Missing");
                        return false;
                    }

                    if (input.UTXO.value != tempOutput.value) {
                        System.out.println("#Referenced input Transaction(" + t + ") value is Invalid");
                        return false;
                    }

                    tempUTXOs.remove(input.transactionOutputId);
                }

                for (TransactionOutput output : currentTransaction.outputs) {
                    tempUTXOs.put(output.id, output);
                }

                if (currentTransaction.outputs.get(0).reciepient != currentTransaction.reciepient) {
                    System.out.println("#Transaction(" + t + ") output reciepient is not who it should be");
                    return false;
                }
                if (currentTransaction.outputs.get(1).reciepient != currentTransaction.sender) {
                    System.out.println("#Transaction(" + t + ") output 'change' is not sender.");
                    return false;
                }

            }
        }
        System.out.println("Blockchain is valid");
        return true;
    }

    public static void addBlock(Block newBlock) {
        newBlock.mineBlock(difficulty);
        blockchain.add(newBlock);
    }

}
