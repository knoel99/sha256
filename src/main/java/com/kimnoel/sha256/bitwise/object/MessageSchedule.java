package com.kimnoel.sha256.bitwise.object;

import com.kimnoel.sha256.bitwise.utils.WordUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Source: https://github.com/in3rsha/sha256-animation/blob/master/README.md#8-message-schedule-schedulerb-expansionrb
 * For each message block we create a sixty-four word message schedule Wt.
 *
 * The first sixteen words of this message schedule are constructed from the message block.
 * Wt = Mit
 *
 * (for 0 ≤ t ≤ 15)
 *
 * This is then expanded to a total of sixty four words by applying rotational functions to
 * some of the words already in the schedule.
 *
 * Wt = σ1(Wt-2) + Wt-7 + σ0(Wt-15) + Wt-16
 *
 * (for 16 ≤ t ≤ 63)
 */
public class MessageSchedule {

    List<Word> wordList;

    /**
     * Fill message schedule with formula
     * W_t = M_it
     * (for 0 ≤ t ≤ 15)
     * @param paddedMessageWithLength512 list of 512-multiple bits words
     */
    public MessageSchedule(PaddedMessage paddedMessageWithLength512) {
        this.wordList = new ArrayList<>();
        int index = 0;

        for (int i = 0; i < 16; i++) {
            // Split the padded message into slices of length 32
            wordList.add(new Word(paddedMessageWithLength512.toString().substring(index, index + 32)));
            index += 32;
        }
    }

    /**
     * Fill in the message schedule from 16th to 64th element with formula
     * Wt = σ1(Wt-2) + Wt-7 + σ0(Wt-15) + Wt-16
     * (for 16 ≤ t ≤ 63)
     */
    public void expand(){
        List<Word> tmp = new ArrayList<>();

        for (int i=16; i<64; i++) {
            tmp.clear();
            tmp.add(WordUtils.sigma1(this.wordList.get(i-2)));
            tmp.add(this.wordList.get(i-7));
            tmp.add(WordUtils.sigma0(this.wordList.get(i-15)));
            tmp.add(this.wordList.get(i-16));

            this.wordList.add(WordUtils.addition(tmp));
        }
    }

    public List<Word> getWordList() {
        return wordList;
    }

    public void setWordList(List<Word> wordList) {
        this.wordList = wordList;
    }
}
