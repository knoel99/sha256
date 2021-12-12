package com.kimnoel.sha256.Utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HashUtilsTest {

	@Test
	public void sha256Test() {
		String expected = "ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad";
		String actual = HashUtils.hash("abc");
		Assertions.assertEquals(expected, actual);

		expected = "ca978112ca1bbdcafac231b39a23dc4da786eff8147c4e72b9807785afee48bb";
		actual = HashUtils.hash("a");
		Assertions.assertEquals(expected, actual);

		expected = "c775e7b757ede630cd0aa1113bd102661ab38829ca52a6422ab782862f268646";
		actual = HashUtils.hash("1234567890");
		Assertions.assertEquals(expected, actual);

		expected = "a0340bf98be5c19ca7c9b33f4dd178d0ecd4ebb834cd3be7a418ff7a7f56d68b";
		actual = HashUtils.hash(hexToAscii("04e00020a695ca9d17a9061b3f11b8a86bbabde295171707d0670200000000000000000034f200f7ce41f26b24239610c8ebad64226f46eb6fbca7b3b8ade86a19c5bca2a921b5611fa20b17b477c0aa"));
		Assertions.assertEquals(expected, actual);

		expected = "71fa7d6e3d9e7ffa52d42e75ca9ce5c22513578b65fb09000000000000000000";
		actual = HashUtils.hash(hexToAscii("a0340bf98be5c19ca7c9b33f4dd178d0ecd4ebb834cd3be7a418ff7a7f56d68b"));
		Assertions.assertEquals(expected, actual);

	}
	private  String hexToAscii(String hexStr) {
		StringBuilder output = new StringBuilder("");

		for (int i = 0; i < hexStr.length(); i += 2) {
			String str = hexStr.substring(i, i + 2);
			int k = Integer.parseInt(str, 16);
			char c =(char) k;
			output.append((char) Integer.parseInt(str, 16));
		}

		return output.toString();
	}

	private  String asciiToHex(String asciiStr) {
		char[] chars = asciiStr.toCharArray();
		StringBuilder hex = new StringBuilder();
		for (char ch : chars) {
			hex.append(Integer.toHexString((int) ch));
		}

		return hex.toString();
	}
	@Test
	public void sha256LongMessageTest() {
		String expected = "2ece3a9ac0e61275fe34d89ea768aff7bf6df0b2bf37f0b08113f9b0fc9bbc83";
		String message = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
				"Mauris tincidunt diam eget turpis aliquet, volutpat volutpat enim accumsan. " +
				"Vivamus id urna pellentesque, volutpat tellus id, lobortis ex.";
		String actual = HashUtils.hash(message);
		Assertions.assertEquals(expected, actual);


		expected = "6314bc42de9ce540e71aa532bf45ccc1c17514584013a45a7329c99c8248d6a2";
		message = "wyzgzJdaqV\n" +
				"MUhFcqojtu\n" +
				"YtSUVSQ57i\n" +
				"AkNbujoE0X\n" +
				"OCVliESTej\n" +
				"22KviAe3nQ\n" +
				"JkxkjsMXkc\n" +
				"e0jVfthJWw\n" +
				"3mCRCsQjPp\n" +
				"0uDvm6vv47\n";
		actual = HashUtils.hash(message);
		Assertions.assertEquals(expected, actual);

		expected = "cb5e8edc830d9cacd8dc9780203f09a6d558ed88eef5936eed786aaf8337d147";
		message = "$N=KwqELP&YrwvDFZ@XE$6=xH8;w2&\n" +
				"tAPqJF8c-53]%vm?XHb.E]!%u6_cUA\n" +
				"frpmjkY.?(V#?2_ec6;:Pjx/!zWVv=\n" +
				"#n75/Cw!{z?kqp@Z4h32FeF*Mjxh_H\n" +
				"+4UGxjV!L)i[qLMB#:;Xg@eSc.MnKC\n" +
				"7.ZJSuMY9*jEFpi_We$CAn5?[E3);)\n" +
				"%8b+i#yS,yMML;]9Wqyf7d!MQkMUz[\n" +
				"T{]V_V-J-v]8$KFrQyyS]?,ZN}Rq5!\n" +
				"]Qc%n+BWuzFp2[:}5R:Sh:u_,{F-wE\n" +
				"-3:-C!ArZD)giCm]mjfb!EXW{tM6eS";
		actual = HashUtils.hash(message);
		Assertions.assertEquals(expected, actual);

		expected = "d5a326e38f4ccc38a90407ccfb1e3374ebaa48179a94dc54d9e5e94c9e30dc79";
		message = "4evfp4ymX#jzGFSa!J!+H#N!7AM[V4ny$$Q+*h-mfSqFLkVRrwhzY$PR((-zM7jvnT8mUzVZ.EfwdU!cM:_Lj!w}v44VH?7x$SJBNp=jn2AL*mn{+Ptn*.z6D9kkh2Y2AqFd}Lz&QdnpQajPC?twPxj;fN3XNaTBMbRQpj;[-!Cw}XQ7Rbf*v_S.mTYPSnAP.}NYv:ySMwm}6qpPjuK4Rj22tCfkz*#.N&kB@WQ._r(g,N99?(Rk7+kUXU@qxf4t{]vx{$]D8?_aPC*GYUe)@;vVHXB7iKv+A6D=/wt&@x7/L7H+cfHrgyh5_dgEX@=U}$CSJF8*M}e;f.*]9y3{{fWL?C7=YYUc.C.TzvE,D{v4,nDgxcJ&yQp(-7GAwm!huE.;#wPepKjJbXn7p3qGXP6EfU[&9=,,=FKjPNP!;+.6%w2Z.-fW{5Ti]$-6tpRd{KPf[*B}rWn5&ba/_H,xu[L&*$xbVQ)iiG9U@:&S)P{+*CNW:f.!\n" +
				"7cEYy582Fr}:4mk69rP-)[d:AKhBY=PAw=X(+P$g?kJHtpqF;V4GnrjSU3a[hqg:}h/;4v=X$B,jxDK(/xS?eF,HF+zM[3R]TbtL&.]QqA[2v94QxGXqF{xgfq:%zSM!n[#BDV5HZ65&HM$ZD%epwDzR=k*yv*AkCkzi@gmjgZExH@:*aD&=Z3/qa/vEY:C=/xqwFybV[_k:Q-rW6A][P;8yb{R4hni}Ta{2nJ[%aSw9g2@#.BBaYr*JTbXm{N_[__umcMnw6]]dE*Ch$$*K),i5*Tj2SxptX%#+/C3G+xt}JFSB!iY$Vmy(?47U3Z(/=PkgkjDuiz)#MKr/&+%dE4acYtv&#Tg+hE{Q4-bW{-G;#bnJqFzd9U6djq=j]6ETb!eLyWW#?+yJ;f.:J7?V@TcgRY.K!/@d(Um6f[mUkt;8*hGkZ*qFr&,=]r?7c5?$..,UC-DM:dLEr([[668C#FDq:5Mq?QJ4Av]$Br]3;7#QRE,b#8Jt\n" +
				"k@-7kJHjmag@qAnB+Y#dyWn(LgSc,drTCEB&)n=(eSbqjC[AMzar#9PZe6z%6:xy!!U9NZ!fSS,!zT6Hh6W(ibi+{{kZja}}fge?H,:J,4p@#i4UFXe/fxtVLHx!+uK_K#S&F,F6{E$XqS&QLU}b6:=U//!X&N(KGVqVr)F](}Q[2[4ejzW_=2]aPPe}K{NkN*:,FLP@$y]3#U4&dmfxS!vt.qu?3-nG[ybqyC]y5)}UG6Xw-w([gEvg7h7Ek,?D;-N}3/mAQXqUEz&#f(=3,=4+j*PWk4Ppc@Rm@8mEy!&Hkfmgn7km%St!;HQfz2@HSY_,(?r2AMKj%2+dZDvg9!H4+NX[6b4_76dezBp([eAhJi5*E6SZpAX,5A_RMvdi@,{d7,whYE!fNSvD4A?&MW:Z_EQt@YCeag2U?/xCt2G!L*chqZ==Au#yJaETL6kQ5ptK24,&X2Jzq%x!@M6dXUu_:A5hYG(tUtNEXpx8E{5hGA6Z#K%$\n" +
				"UHbA_en:BS5GACd4M%3!(6]:@b5x%)vLJdZ/d&++(AVZU3]bjr8C=&@*)Gmc(+PR]}ArC&(ZH29@/A,4-(uxQj)xAFk,Lp.2gwc=2bCFv]QVDj55gCuh%iM9Sa:_uwPH%%T+]YjRr*VXwpfYe(3G&?G7?#(5V@j[eetbHX:tfB-A)$)9K.b$ni+6:iHU{8bg%ff,:_2xCv;qDk4-Z}Z*.J$N}&S)ijQh3H/44u4ANWytccAfYtY%k:i,dXyjghkJ:n$8z%3@UxcQnTcTb=4(;;h%;K%D!Lw,})ZS_PPAUA{:p$BdmbG$-8Z6PjQrMxW{S%$iSj$aL(CnJWVkXbccr9kjeHHfqVPqHUHCPx5+4TaLNy/JU+7X2d;Q6Mm.C#6((2Nr,FDVXc=P#Y+i4fwmx/]Yp,i(bF(2*)i5yW}VS3N+P;b3-Vej-Ah!t@v&#8}vA:&(AgX5PUcR=v%].e$8@v&h3[!_i,Z/_Ky-@fmd;!B4@@r?vHBN\n" +
				"?,x!DJ$98R#WkT?$x;3#YaY3(wCBbFuRLw#yyUVGu#tJ;x:9ip@-GGd&hJp-n4(xn!=F2rNx8#Va#4:u(/]!i([q*299a=M%2m?6T[r*fJm!99kvUC%rVQQ*vQ._v6r9M!SU2qFi2*)ge!NQU.!Lg]V4EBRtv(U;#RRn#zT-XiH75h&k(D76J-VWDGb]wudc4vJhF2K)Dny4+m)rKD2/*.Kc4R{9iDKmnLGwgWDN8g:6i!xFcKkqfr+B%QkR#2Q%bZ9Q/FYDf.4AuLDS%Q,hyDQD/h2-$:?3VD(82P(3aJ&gM?p)fVM9cqAE7Y9q:h7]?hW.9.[kfYg!MM)S$Bp2XYn$;i]]_p[_[}5@b$SBjPQHp(+xz)$Tj/RJA8H]hEF]8JFWiTt8qq:wHqB)W)W.@WF2Ug){}$DXu!Vw)z?.@(,LzYqeagguwNza$knaV=&ym/qE_7E,GnVvU)h[AUV*?*,Qt:8W$:BRT#%{:y(29fhTf@V3b@;U\n" +
				"BJ;ppEVbZBvDw+k$wD.vHXLhCY6SY&U5,hp{i_w(8en.H,VqX@WM7r*7%%vi4PNwba*MDnBfiY2jS9tHRWTtfX%N/Y]cK4H%&F.}KBLGBCe4RHF$@8Ci[Btg(fv$k}jBHZYFKx6PR2/@5Zt,,buF#Y?aPfC{uT?3+V@-iHZQq3)dC)Q%UwqevXRRFWW#hLQQ@xT}#hvw+#p?6eUqNMt2S!?[#XaiJB3]p@3ReRAnZ52FDV=w,x[qpyM/9i@QPED-yD25jW-?LVE-qDT+T!iN[:Tv6:Y;Lcx+u,kxqu&uh_A]@)E$)S6QKP;QYe=Hu3yt@Y&Ddwqg9qaQ{];*S5xG:=,}@P8;9P/Xvmge,S#raxJqQRWKr;YnV!k)%.@@Cg*}bk9X:TWe4ccK8FwmDr88YqdkLJcjSxAV&nk6[=jr#az:fH/Fm?qH?MDurzPX}m4599=4k_(-!2u}G_EA=[?ud%txRhC!gC&h#aQn_m}%cX]Fm?Xe.m+$\n" +
				"{Fbn%Q$E-2vVAD!MPG6:;Gi:C&]C:cL5DqgYWR7E)SA-4h8qTmbSZLU4tj,qiiUy+7V94gv4TQ.+/r#yq5BWx%vS2}/Ce;YYQV6@)2M};M,m&k{Gw5?Q3QZBMBdiW#q92V6Z8!v@gGV]w]XkaWHXw2.t_9_$n}P4ZVDDk-6R;){2SVw:uPcQmqnyA{:)G.f{Wtg#c,D,6WWzWY@}aNNa:;hz%Bjhg7wS/djnJV&nAXtX]!uR_e4NM)n;XZ?qJ-Hm(5CBi})qg++?8}65A;DE!Xu$4(a}E=@&*3vX[)==_bGNY]Lbd,65GaxK/fp%mB_-3$NNWHPW?34JPp[TTNcMZQ:Mc2TVCQ$DESL:,XCuA[EQ2J.358,%?Lt/eqX9hfRn::yxv!5!@n#4]#=yXAXYR=EMy)iju5{H&.Ch7&#r@SxZ8=q),3u)h@.P%)pFTk)4$abP@$gm8xp)w8i(D!PLLW.-]$L/hJV9UN$=+,rn2Y.:9*28C6V4\n" +
				"7#UCdwC*f$TeK]bw-ybVi{+9kFrVC7,Mw$#xxp4%2k6x_gxRm&JfJ[MZJQ[)%WXeU_7qq5H=U7[pUc9:*+*f8xFJNt?{!]M{2FwrRk6h?M{=)nYb)HXB(@dBQ&znt}A%7d2n)+xZ3]{Ne(p+rYE7kJ].,=dD8GD8Xf+K}A.SmS)G2DimkT(*,c)%?/Ee5Fj;]u?x)Y;!&RV9.jfAVV&QyYdT)m$/F%VwYYk9yq=.c]jmh6MKR[9]ydG[*S%$T[])+vag&}Gcm+*JjN_2PS,QydECSEk&8p2emRx7FcNR?wvBzUXSVG{5eEQja=ka[88r9kg7+a#wrj4}QSm-H=$rm*i-,tB5K_6j=Q-rw#k+MG]b,t{Mik6WLkFeba}F%PJ;5!-v!y*z:&b4Cmy=c8)y_z*2@2JrubW{vJqM},$e%+k,,tY7BGUP}3PUiV]crPAaFS:FjqjPV3SbpLzWY:[yTX;ZW7u+:QXiir8)Xjne?R(4%BA2f9CL\n" +
				"GWC/+_azWmYhug(fmZ!ZxH*#Aq_2U7&3&ZudF9yr3xRPAHwgbjZ&@Ny?ChHuBRrE.MW$Ewaqz+tRYB]8NEM4Mh&L}}}/d]rBu;5#zD?DDK8qy!TmP,pXYzhK.KJF(TV]bhWcW;N8.84Q{9Pa_6e%amy.Aw6T!rLe{4H./i9Q&*]}N;n:cw2:nY;rc#Mq)cB!5gNn&P+KHQdck@B:-.ZLU.w8RR[DT[}!KiJ7_J-!p!#kTBqjn}_[9MV*}m&)#.U}[@+tf%y.twk=7.$Kd2qX{q#[xaG8JULSz}9KK4PE,h=}@@Qe8!$?R)aYSHXKnv]{aG5d4zS6D5Y3;eS#];nftmHA?T.gfn:{!x$NN/T@m3tKjC,En?w8a#imeXtnCVt4DeVZ4Y{;vp[n{933wP?-S[MC6SLp:wkiW#4$)&]-,J)$Z_)vk+FXPrhjg$W6{=F/yt(/yKUgPZ@Pv#5Ei?pu?:Cz!ZChcB?[P_[fb9E!&_Z6;J/;$iQa\n" +
				"UiGjC{#C/V{@W#KC:Ahw7YVZ-A?UayWnAU2PMz6a{AMHN#%#EuR;a.eUeHW,xm]pPSxcGMd!jMSRaPn8B9CYJ-E(8Czi$(P4&!p6QYv@/*MNwf5ueWWU4AKj[rX/.;k!yh.)rpeL$zhTP(u5Lz5;8pL,v3X%&qU;3fxS2k)MJ5ST=DGe{:+n{pExyrPSj6-[a!,UKx.{iHZ-$p-uy-&5W.eibaT/W,P-)WL54(fP=@bzQ4Cp!A*Nd}AwYk%un}uC3;TrGH5W)D$W6#e?mdq!2pr)Qw{DLfcH)8mt??Sy@%%JckfgRk#d)z9SCu$Sz9a)K][ba!ry&z-wx@CR2[FD=JP-yA7}tCM/wL&ZtM)RP!3U/.C$f?v+WbW]GwF)9[gT.kH[[/]]Tm&Qg79P&}JC2+)m:d[4Rb(Dewg5(?y)y,3h2dE}u%JedezD}fAjv*#b(Kv2/WYZ-LkrPHv&nwUK]e7!kE/7K!:GC=y8*3pJZhk8bfQyAYjx";
		actual = HashUtils.hash(message);
		Assertions.assertEquals(expected, actual);
	}

}
