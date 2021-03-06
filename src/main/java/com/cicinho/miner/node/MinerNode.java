package com.cicinho.miner.node;

import org.ethereum.core.Block;
import org.ethereum.mine.EthashListener;
import org.ethereum.samples.BasicSample;

/**
 * Miner bean, which just start a miner upon creation and prints miner events
 */
public class MinerNode extends BasicSample implements EthashListener {
	public MinerNode(String logger) {
		// peers need different loggers
		super(logger);
	}

	// overriding run() method since we don't need to wait for any discovery,
	// networking or sync events
	@Override
	public void run() {
		ethereum.getBlockMiner().addListener(this);
		ethereum.getBlockMiner().startMining();
	}

	@Override
	public void onDatasetUpdate(EthashListener.DatasetStatus minerStatus) {
		logger.info("Miner status updated: {}", minerStatus);
		if (minerStatus.equals(EthashListener.DatasetStatus.FULL_DATASET_GENERATE_START)) {
			logger.info("Generating Full Dataset (may take up to 10 min if not cached)...");
		}
		if (minerStatus.equals(DatasetStatus.FULL_DATASET_GENERATED)) {
			logger.info("Full dataset generated.");
		}
	}

	@Override
	public void miningStarted() {
		logger.info("Miner started");
	}

	@Override
	public void miningStopped() {
		logger.info("Miner stopped");
	}

	@Override
	public void blockMiningStarted(Block block) {
		logger.info("Start mining block: " + block.getShortDescr());
	}

	@Override
	public void blockMined(Block block) {
		logger.info("Block mined! : \n" + block);
	}

	@Override
	public void blockMiningCanceled(Block block) {
		logger.info("Cancel mining block: " + block.getShortDescr());
	}
}