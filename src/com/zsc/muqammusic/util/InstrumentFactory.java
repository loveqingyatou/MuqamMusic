package com.zsc.muqammusic.util;

import java.util.ArrayList;

import com.zsc.muqammusic.data.InstruConst;
import com.zsc.muqammusic.model.Instrument;


public class InstrumentFactory {

	public static ArrayList<Instrument> getInstruments(){
		ArrayList<Instrument> instruments = new ArrayList<Instrument>();
		Instrument ins;
		
		for (int i = 0; i < InstruConst.DRAWABLES.length; i++) {
			ins = new Instrument();
			ins.setName(InstruConst.ICONNAMES[i]);
			ins.setIntroduce(InstruConst.INSTRODUCES[i]);
			ins.setImages(InstruConst.INS_IMAGES[i]);
			instruments.add(ins);
		}
		
		return instruments;
	}
}
