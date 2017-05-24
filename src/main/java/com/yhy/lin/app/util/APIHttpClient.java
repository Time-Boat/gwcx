package com.yhy.lin.app.util;
import java.io.IOException;  
import java.nio.charset.Charset;  
  
import org.apache.commons.logging.Log;  
import org.apache.commons.logging.LogFactory;  
import org.apache.http.HttpResponse;  
import org.apache.http.HttpStatus;  
import org.apache.http.client.HttpClient;  
import org.apache.http.client.methods.HttpPost;  
import org.apache.http.entity.StringEntity;  
import org.apache.http.impl.client.DefaultHttpClient;  
import org.apache.http.util.EntityUtils;  
  
import com.google.gson.JsonArray;  
import com.google.gson.JsonObject;

import sun.misc.BASE64Encoder;  
  
public class APIHttpClient {  
  
    // 接口地址 
	//登录接口
	//private static String apiURL = "http://localhost:8080/gwcx/app.do?appLogin";
	//上传订单接口地址
//    private static String apiURL = "http://localhost:8080/gwcx/app.do?createOrder";  
    //线路站点信息地址
    //private static String apiURL = "http://localhost:8080/gwcx/app.do?getStationList";  
    //获取机场站点或者火车站站点信息
	//private static String apiURL = "http://localhost:8080/gwcx/app.do?getPTStation";
	//取消订单
	//private static String apiURL = "http://localhost:8080/gwcx/app.do?cancelOrder";
	//完成订单
	//private static String apiURL = "http://localhost:8080/gwcx/app.do?completeOrder";
	//用户意见反馈  
	//private static String apiURL = "http://localhost:8080/gwcx/app.do?feedback";
	//修改用户个人信息
	//private static String apiURL = "http://localhost:8080/gwcx/app.do?updateUserInfo";
	//获取用户个人信息
	private static String apiURL = "http://localhost:8080/gwcx/app.do?getUserInfo";
	
	//测试图片
	static String strImg = "/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAYEBQYFBAYGBQYHBwYIChAKCgkJChQODwwQFxQYGBcUFhYaHSUfGhsjHBYWICwgIyYnKSopGR8tMC0oMCUoKSgBBwcHCggKEwoKEygaFhooKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKP/AABEIAWQAyAMBEQACEQEDEQH/xAGiAAABBQEBAQEBAQAAAAAAAAAAAQIDBAUGBwgJCgsQAAIBAwMCBAMFBQQEAAABfQECAwAEEQUSITFBBhNRYQcicRQygZGhCCNCscEVUtHwJDNicoIJChYXGBkaJSYnKCkqNDU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6g4SFhoeIiYqSk5SVlpeYmZqio6Slpqeoqaqys7S1tre4ubrCw8TFxsfIycrS09TV1tfY2drh4uPk5ebn6Onq8fLz9PX29/j5+gEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoLEQACAQIEBAMEBwUEBAABAncAAQIDEQQFITEGEkFRB2FxEyIygQgUQpGhscEJIzNS8BVictEKFiQ04SXxFxgZGiYnKCkqNTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqCg4SFhoeIiYqSk5SVlpeYmZqio6Slpqeoqaqys7S1tre4ubrCw8TFxsfIycrS09TV1tfY2dri4+Tl5ufo6ery8/T19vf4+fr/2gAMAwEAAhEDEQA/APl2yhhnE4llkjkWJniCxhg7DkhjkbRjPIB5wMc5ABbvNIe10+wmlcrcXRdvJZcbECoyEnOcsGyBgcFSCd3ABpQeHrK5lsXtbzUJ7W9uHs7cx2KmWSZGjyBH5uMbJUYHd975cY+agCx8NfCSeL/GUOkSXiw2wDSSyp950XqIw3c8dRwMnBxigD0D4r/CPRfCfhGXV9L1K882GVFMV5IjeaGOMJtVfmGd3fgGgDjPBfgmz8R6ek0eoXL3JJWW3t7VpDD82BkJvc8YOSipzjzAQQADlvEOnJpeqPbRSTSReXFLG8yIjMskaupKo7gcMOjHj0PAAM2gAoAKACgAoAKACgAoAKACgAoA7Lw14IfXNIe+hu1ARSXCrv8AL+998puEY+U8zeUvUhiASADDtNISfX3083cawRmRpbhQJAqRqWdhtJDEKrYwxBPQ4OaAJLaDSry4nhto71dsM0sckkyHOyNnGVC99vr360Abvgr4eXvivTLe7tb23gNzqDafCkoYjcsDzMzEdBtUAYBySemOQDqNU+BesaVpOoajeatpxgs7Wa5ZYQ7M2yNmCgEAckAZzxnPPSgDz/SfDy32kG8e6MUkn2ryI1i3hvs8Imk3nI25VlAIDZOc7QMkAqa1pkVha6PPBcPMmoWf2kh4ghjYSyRsvDHIzESDxkEcCgD0CP4DfEyNiyeHFBKlf+P+16EYP/LT3p2A0734SfGK/wBOgsNQ067u7KBt0UE+rwOiHbtBAM3GB09Mn1NFgI7j4M/FG4d/O8L2xhfe3kLeWqxq7AbnVRKNrEqp+XA4C42/LRYB+lfCD4tab4kh12DQy2oxz/aDI+o2/wC8YnLbsSgkNkgjPIJHeiwFrxR8MfjT4paM69p8t2seNkZv7RIweediyBc8nnGaLAZNp8Dfifaeb5PhuE+YhjYSXVnIACMZG5zhvRhgjsRRYAvvgZ8TbyZZZPDYDLFHF/yErduERUHLTE9FHHQdAAAACwFf/hQXxK/6Fwf+B9r/APHKLAH/AAoL4lf9C4P/AAPtf/jlFgD/AIUF8Sv+hcH/AIH2v/xyiwB/woL4lf8AQuD/AMD7X/45RYA/4UF8Sv8AoXB/4H2v/wAcosAf8KC+JX/QuD/wPtf/AI5RYA/4UF8Sv+hcH/gfa/8AxyiwB/woL4lf9C4P/A+1/wDjlFgD/hQXxK/6Fwf+B9r/APHKLAH/AAoL4lf9C4P/AAPtf/jlFgD/AIUF8Sv+hcH/AIH2v/xyiwEsPwJ+JkMc6L4ahImTYxe8tGIG4NlSZMqcqORg4yOhNFgHWXwM+J9nP5tv4eUOUeM5vrUgq6lWHMndWI/GiwEkPwR+J0DM0PhiBGZHjLC9tc7WUq3/AC19CaLAaPh74XfGXw9EY9H0o26GUThTeWb7ZNrJuXc52kqzKSMZBwc0WA1r3wh8e72yubS6t5Jba4ieGVGurHDIylWH3vQmiwHK2nwO+KFoJTb+HRHJIjRmQahbbgjKVZR+96EEg+3HQnJYBbv4IfFG7t7KGbw8pjs4TBCBfWowhkeTH+s5+aRvzosB9yUxBQAUAFABQAUAFABQADk4oAyxr+mnw4uvC4J0prP7eJxG3MGzfv243fd5xjPtmgCsPFmjro9xqt1cTWNhbzLBNJqFrLaeWzFQu5ZVUhSXX5iMc9eDQBp6lqFrpunXt9ey+Va2cbzTvtLbEVdzHAGTgc8ZoAq3OvWFqL5p3nRLPaJH+zSbHZjhUjbbiVy3y7ELNuIXGSBQASa/p0Vha3lxNJBDc3K2kIngkjkeVpPLC+WyhvvZ7YwN33eaAHWOuWF7qM1hDJMt1FvOya3ki8wK2x2jLqBIFbAJQkDcufvLkA0qACgAoAKACgDntd1u6s5I1soLIqX2b7y8+z72/uoNjbz7ZXp160mwNqzuPtMO8xtG4OGRux/qPemBPQBlatezwzrBHHKiFd5uFUlVxn5SdpAz64PfjOMpsCfRrt76xE0m05dlBUYDAHGcZOPpk/nkBoC9QAUAFABQAUAFABQAUAKDgg0Aee2PgGWy+Gx0OK4kbVX0FtLdpdRuZLbzTAEyqsSEXcOqoCBwB2pWAl0/wjeyx+VdgaZBNPJdXa22qS6g08nkrAodrmI70aMtlCMKY4yM84YEuu+DbjWPCGoWF3eXT6rJYyWUM/8AaVwkcp8sokkyJtQluGcbG6lfmAGSwFAeBbqGbUTa2+lxu+oNqaXSyukt5J9uS8SKdQmAFKtEHzIQDuUDLIQDd1DQLzVIIbm7aOHUBdW85gjuWkt4xHcROxX5FLOY4lGWXgkgbQzEgEthoV0ni6bVru4kaCGOeCzia4EuEmaF5M5jVlw0IwC7gBiAVACgA6SgAoAKACgCveXBtkRhE8gZgp2kDb15OffA4zyR2yQm7GtKn7R2vb+v6/4fQxWe3a4L3GkPLJL8jvK0TFeAQuN2erEYGeVPYDMc3kbPCrW0/wAH5+Xz+a87WtKuGidbRLGeOAHCMzqQowvHXPc+vAOcHimpa2sKeHUY83Nf5P8Ar+uprSMUjdgpYqCdoxk+3NaI5GYN9N/aEcIutM1Hy/Mw9uREVIGclsMdy8jIBOemDyKfJ5i5vIk0ydbJfskNpfNGJNgeVo9vccfN04Bxj+MYHBCnL5hzG5UlBQAUAFABQAUAFABQAUAFABQAUAFABQAUAFABQAUAFAFa/sbbUIVivIhLGrBwCSMEdDxSlFS3NaNedFuVN2exnt4Y0drwXbWY+0BxJv8AMfO4HOevqenep9lG97HQswxCh7Pm0tbZbfcLp/hvSdP8n7Ja7PJfzI8yu21tu3PJPY0RpRjshVcfiKqanK9/JevY0r21hvrK4tLuMSW88bRSoejKwwR+INaJtO6OJq5n23h3S7ZrFobYq1iuy3Jlc7BzxyeRyeuapzkxcqKv/CG6D9gWy+wf6Ks32gJ50nD+X5ec7s/c+XHTp6Ue0le9xckdjoKgsKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAazqOpoAia4UUARm7FADftfuKAHC7FAEq3CmgCRXVuhoAdQAUAFABQAUAFABQAUAFABQAUAFABQAUAFABQA1mCjmgCrNdAdDQBRlu/egCBZJpv9VG784yBx+dFwJVs7tycqqf7zf4UrhYd/Z91/fi/M/wCFFx2GtZ3aEYVX/wB1v8aLisRNJNCf3sboM4yRx+dO4E0N370AX4brPWgC0rBhxQA6gAoAKACgAoAKACgAoAKACgAoAKACgCOWQIKAM25uevNAFWGOa7b93wmeWPT/AOvSbA0YNOgj5cea3q/T8qVx2LlIYUAJuXONwz9aAFoAKAKc+nwSjKDym9U6flTTFYzpo5rRv3gymeHHT/61NO4i1bXPTmmBpRSBx70ASUAFABQAUAFABQAUAFABQAUAFADJHCLmgDKu7jrzQAyzszc4lnyIuy92/wDrUmwsbCgKAFAAHAA7VJRXuLuOHj7z+goApvdTzEhMgeiDmgQCznkYl8A+rHNAC/2fL6x/mf8ACgBDZzxsCmCfVTjFAAl1PCQJMkejjmgC5b3Uc3H3X9DQMsMAykMAQeCD3oAx7yya2zLBkxdSvdf/AK1UmS0PtLjpzTA1Y3DrmgB9ABQAUAFABQAUAFABQAUABOBQBnXk3WgCnZwfbJiz/wCqQ8/7R9KTYG0BgYFSUZ93eHJSE4Hdv8KBCW1juAabIHZR1/GgLGgiKi4RQo9qBi0AFABQAjorrh1BHvQBn3NiVBaHJHdT1/CgQtpeHISY5B6N/jQBoEZGDQMxLyA2c4ZM+U54/wBk+lUmSy7ZzdKYGiDkZoAKACgAoAKACgAoAKACgCG4falAGJdOzuETlmOAPegDatoVghWNegHX1PrUFFfUJ9i+Wn3mHPsKBMbYWwAEsnU8qPT3oGXqACgAoAKACgAoAKAKN/bAgyp1HLD196BDtPn3r5bfeUce4oAsXMKzwtG3Qjr6H1oGY1q7I5RuGU4I96sk2rd9yUATUAFABQAUAFABQAUAFAFC+frQBU0tPMvHkPSMcfU/5NJgjWdgilj0Ayakoy7dDc3RZ+mdx/woEatAwoAKACgAoAKACgAoAKAMq4Q210GTpncP8KBGorBlDDoRkUDMnVE8u8WQdJB+o/yKpCZbsX6UxF+gAoAKACgAoAKACgAPQ0AZF+3WgCbR022pc4+difw6f0qWNEuotttiP7xA/r/SkNjdMTbCWxyx/Qf5NAkW6BhQBwnxE1zUDqmleFvD8wg1PVcmW5H3raAZyyjjkgPznI2HHJBHFiqsuaNGm9Zfgj6fIcBh/Y1cyxivTpbLpKXRPy1XS2uuiaK1x8ObuG1kn0zxZry6xsUrNPdFoncY4ZQM7TjHU4z/ABYwYeCaV4zfN6m0OJ6c5KnXwtN0uyjZpeTva/y+7c1/hp4hutf0BxqqbNWsZmtLtdoUl1/i2jpkdegyGwAK2wlZ1Ye/8S0Z5/EOW0sDiU8M70ppSj6Pp/WtrX1OfDav478W6vaw6nfaV4b0qU2zfZf3UtxMAQ2HGeAckjPTZ8uWyMPfxVSUVJqK006s9VfVcjwVKrOlGpiKq5ve1UYvbTu1t1vfWys4fEWk6l8PbCPXdF1rUr+xtnVbuw1CcSI8bMBlDj5TkjkDPOemQZq054Ve0pybS3TNcDjcNxBVeDxdGMJy+GUFZppddXdW6X/GzXplhdw39jbXlq2+3uIlmjYgjKsAQcHpwa9GMlJKS2Z8bWozoVJUqitKLafqtGT1RkVNTTdCHxyp/Q/5FAh2nNutgP7pI/r/AFoGiLWE3WgYY+Rgfw6f1prcTIbFulUI1x0oAKACgAoAKACgAoARvumgDFvz1oAvaWMWEX4n9TUPcaItVP8Aqh25/pQgLNmu21jHtn8+aBomoAKAOEmhjl+N8LyIGaHQi8ZP8LecVyPwYj8a4Wk8Wr/y/qfTwnKPDskno6tn/wCAp/mju67j5g8w+E3/ACOvxE/7CI/9Gz15uC/i1fX/ADPteJ/+Rfl/+D9IB+z2xfwbfM5JZtRkJJ7ny46eWa0n6/ohcdJLHU0v5F+cjs/HShvBPiAMAR/Z8559RGxFdWJ/gy9GfPZK2sxw9v54/mip8MZnn8AaG8hyRbBB9FJUfoBU4R3ox9DbiGChmddL+Z/jqdPXSeMQ3i7rWQD0z+XNAFfSjxIO3H9aBIk1QZsJfw/mKFuDKNielWI2k+6KAFoAKACgAoAKACgBG+6aAMa/HWgC7pRzYRZ9/wCZqHuNEeqA7Y27AkUICxZMWtYyfTH5UDRNQAUAefX90bT446arxsVvdIa3RscAq7yH9E/UVwSfLi4+a/4J9Xh6Sq8O1bPWFRSa8mox/X8Geg13nyh5l8Io2k8S+Pb6PD2dxqZWKZSCrkPKxwR14dT+IrzsCr1Ksujf+Z9nxTJRwmBovSUYarqrqK/NP7h3wJi+w6HrWlzOhvLPU5EmRWyV+VVz9CUbH0NGXLlhKD3TFxpP22IoYiK92VNWfzb/ACa+9HUfEe7isfAevSzkhGtJIRgZ+aQbF/VhXTipKNGTfY8TIKMq2ZUIx/mT+7V/ghfhzb/ZfAmgx5zmzjk/77G7/wBmowseWjFeQs9qKrmVeS/ma+52OiroPJIb1itrIR6YoAr6WDtkbsSBQJD9VOLCX8P5ihbgylYDpViNpfuigBaACgAoAKACgAoAD0oAyb9etAEmjPm3dCeUbp6A/wCTUsaJ9QTfbEjOVOaQyLTHzGyHqDmgSLtAwoA5fx74Sj8U2VsYrmSy1Oyk860ukz+7bjII9DtHI5BAPqDzYjD+2Sadmtme1k2bvLZzjOPNTmrSj3Wu3mrv1v6NY9xp/wARr3TxYz6l4ftldVjlvbYS/aAvRmHAXcRnoF9tvbFwxco8rkl563PRhieHqNX20adSVtVF8vL5J63t6387nTeD/Dtr4X0G30yzJcJlpJSoDSuerHH5DrgADJxXTQoxowUIniZnmVXMsTLE1t3suy6L+uupg634U1W18Rvr3g28tbS7ucLe2t2D5E4H8XygkNwOmOpORk7samHmqntaLs3unsz1cFm+FqYT6jmcHKMdYyjbmj5a209b+j0tSv8Awp4l8VX9pH4yvNLXRYGErWemCQec4zjczjIGCc4PQcAE7hnLD1q7SrNcq6I6aOcZdldOUsshP20tOadtE+yTs9tLr520fotegfIhQBS1N8Rqg6k5oES6emy2Gc5Y5oGiDWXxbogPLt09QP8AIpoTI7FelUI1h0oAKACgAoAKACgAoAKAKV8mc0AUNPk8m92k4WQY/Ht/X86TBGyQCCCMg8GpKMlCbS6+boDg+4oEawIIBByDQMKACgAoAKACgAoAKAAkAEk4A70AZLk3d3xnBOB7CgRrAAAADAFAzG1CTzr3aDlYxj8e/wDT8qpEsvWKYxTAvUAFABQAUAFABQAUAFAEc6bkoAxLyMqcqSCOQR2oA1rOcXECuOD0YehqGUhl9b+am5fvr+vtQBWsbrZiOT7vY+lAkaVAwoAKACgAoAKACgDNvrrfmOM/L3PrQIs2Nv5Kbm++36D0oGPvJxbwM/Vuij1NG4MybOMs2WJJJySe9WSbUCbUoAloAKACgAoAKACgAoAKACgCheQ5B4oAzoJWsrgtjKNww/rSauBuIyugZCCpGQRUlFS7s/MJePAbuOxoEV4LqSA7JASo4weooA0IZ45QNjDPoetAySgAoAKAI5p44h87DPoOtAGfPdSTnZGCFPGB1NAixaWYjIeTl+w7CgZbdlRCzkBQMkmgDDnma8nDYwi8KP61SViTRs4cYpgX6ACgAoAKACgAoAKACgAoAKAGuoZcGgDMu7frxQBWtriSzbaQWiJ5X0+lJoEbEUqSoHjYMvqKkoSaCOYfOvPqOooApS2DjJjYMPQ8GgQwfa4jx5n/AKEKAF868z/H/wB8f/WosAh+1ynB8z/0EUAPisHODIwUeg5NAF2GCOEfIvPcnqaBiyypCm+Rgq+poAx7m5kvGwAViB4X1+tUkTcs2lv04pgaaKFXAoAdQAUAFABQAUAFABQAUAFABQAUANkQOOaAM+5tuvFAFALLbyb4WKnuOx+tFgLsGpr0uEKH+8OR/jU2HcvxypKMxurD2OaQx1ABQAUANkkSIZkdVHucUAUJ9TXpbqXP948D/GnYVyltluJN8zFj29B9Kq1hF+2tunFAGhGgQcUAOoAKACgAoAKACgAoAKACgAoAKACgAoAQgEc0AV5bYN0oApS2ntQBUe0IORkEdxQA5Wuo2ys0n/Ajn+dFgHfaLz/nt/46P8KVkFxjNdSNlppP+AnH8qdgES0JbJySepNAFuG09qALsVsF60AWAABxQAtABQAUAFABQAUAFABQAUAFABQAUAFABQAUAFACEA9RQAxolPagCM2ymgBPso9qAHC2UUAPWJR2oAeAB0FAC0AFABQAUAFABQAUAFABQAUAFABQAUAFABQAUAFABQAUAFABQAUAFABQAUAFABQAUAFABQAUAFABQAUAFABQAUAFABQAUAFABQBz/jO5kbS30iwEEuqamrQQwzAlQhwJZXC87EVsnlQSUTcGdcgFPWfEL3OmW1zZWZlWO62ajA8DXE9iyxM65ihYs7LN9nyF3ZV9w+UhqAOF0LxrJe6B4s1qz1DTLeS9059bVdPukv5rDZawIVliZEAl+QkAsVyCCOOUB0HgvVdOfxDp+l6J4j0jUCtpNLcw6ZObiJ0V1+d90jMkxkn3eZkmQeZv3HYyAHcaLLHNYbodRbUVWWVDcNsyWWRlZfkVV+Ugp0z8vOTklgYviRLyTVUsrC6vYZ9RgHlSxj91aNAzPvbnkOXjQjHIFc1bm5uVN6/hbX8djkrc/Pyxb1/C2uvrojOuNesHs7O9u76XT5dVkkltTNfmCOOFWUCXDHbyixMIyDlnIICtI1ZuqrJydm77u1vP8tP+CZyrRsnJ8rle13ay7/dbTv8AMtWZnudYv7GDVTNcAyy7g0ym0ZZlaOOWMvgqwOBjZuRGxkNkWruTinr89Nevr8tCk5SnKCld6vrpronrs/lotNza8MTPd6Lb38zs0l+ouypYkRhwCqDsAq7V4AyQWxljW1JtxUn11Oii3KCm+uv9f15mrWhqFABQAUAFABQAUAFABQAUAFABQAUAFABQAUAcf4i8bWOg6rHa3T6f5r3ZgdFvUEywi1eZXdW2hCZFEYDHHzqdw3YpAF542NrLYwppM+pPPa29w0um3VvLAvnMVjCySSRkqzKwVioDcY5yAwCTxC1voFm+r+GL2yjk22jW+yO6jjVvLBUCAuSpBbb8o3GPaQCyBgA8OeM49etr68TSL8GylmhDyQNCGVbiWM7WmCDOIUZlz8pYKeVOACfwj4sfX5Z4X0u5tpYpGLFpIiohLMYZOH3EOq9QCA4dM5RsAHRWNyt5ZW9yiTRpNGsgSaMxuoIzhlbBU+oPIoAxvFniM+HrWedrJ544rWS43edHGpZWVQuWYd2HYnpgMeKwrVvZK9unkenluXLHTjBTs3JLZvo3fRPt/nZaiL4iknimazs1laHUGsZFNwvGJFTeNgYj7ykqwUgZJ4xk9s3stnb8fn+NinlsYNe0na8FJad03b3uXs1dcyelru9tbS7wX9s8yrtCzzQ4zn/VyMmf/Ha1jLmVzz61J0pKL7J/ek/1LdUZBQAUAFABQAUAFABQAUAFABQAUAFABQAUAFAHD+I/B+r6prdxe2fiH7HBJt2wbtQG3CgH/VXsSdQTwg685OSQCPTvhzbWsemeddxzz6fYaVYxTNajcv2OV5GZTuO3zA20jtjq3SiwHQa7oA1O2tWSWJNQsnEtpNLG0kUThlIYxb1BOF27shgrOAQGYEAqXXhNbmw+wS6jcyWgktbhHlJknWeBkYOJGOAGMaEgKDkyHOXyCwDNM8M3sWsQahqt9YX00E0ssJWykQxeahEm0tO/JITaxBKpvQYV8KAdJZxSQWkEM1xJcyxoFeeQKGkIHLEKAoJ68AD0FAGL4i8NRa0bwvIkDXFi9kJY4yso3HOS4YFlBA+TgHLAkhsVjUoqbb7qx6OEzCeG5FuoyUrX008mnZvuWl0ZY7W6iinkLzSyz5leR1Du+8DG8HYCACgIVhkdzT9lpo+/9b/gZrGXknKOiSXTZK3Z69pWbTs+iJNI0lNKaYW8paOc+bKGUbnmP3pDjABbAJAAGcnuacKfJe39Pv8AMnE4p4lLmWq0X+Hou7tsm23ay6I0q0OQKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKAOfmupF12O3t9RgRpZQ7QTNlmjUMrBEwCMkLyCR8rHsdyAgt/wC0DqULT6iY1aZxFCXUGVdyMw2tGu4BQcY5XB+Zg3ygGDJf6kb8q1ze+WdFlmGyZSh+UbZM8H05xuDdyvJAOqhubuCxigENw0kJt4vPOHMuWVZDjcxG3JySfU5OM0ANaVr7SZzbJc3EDSb42tLsBpB5hyFkyMfdORkAKcKeOACLR5IptNEkC6gVmuD5Qa888r8oViHDEbB8xwWPzZxztFMC4buS7fSLqwmZrWaVlmWPZIu3y3PzMM8h1Aypxk454oA1qACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAKACgAoAM0AFABQAUAFABQAUAFABQAUAFABQAUAFABQAUAFABQAUAFABQAUAFABQAUAFABQAUAFABQAUAFABQAUAFABQAUAFABQAUAFABQBk3XiPRrSDzbrUraBDN9nAkbazSb2QIFPJJaNwMDnacZxQBb07ULfUFma1MpEMnlP5kLxkNgHA3AZ4YcjjOR1BFAFugAoAKACgAoArG+tVvJLVp0W4jjWVkJwQrb9p/Hy3/75NAEltcQXUbPbTRzIrvGWjYMA6MVZcjuGUgjsQRQAkF1BcSzxwyq7wOI5FB+621WwfwZT+NAE1ABQAUAFABQBV1G/g062a4uzKIlVmYxwvJgKjOchQT0U/U4A5IBAGzalZwxxSSXCCOWYW6v/AA+YThVJ6DJwBnGSVA5YAgFygAoAKACgAoAKACgCK6t4bu2lt7qKOa3mQxyRSKGV1IwVIPBBHBBoA5Sx8AaRp1ncxaZDBYSXExld7SLyVYb5WRWRCA4UTFRuyOFOAQuCwGl4O8OxeGdNms4BZBJJjN/olmtsuSqggqpIOCMA9doUMWYM7AG9QAUAFABQAUAYF5oE8/iK41SDVrq0861itjHBHGcbPtGGy6t0M4YDA5QZ3AlaLAVPDHhebR5JJPtzx+Zqd9fzQ26xiO58+Z2XzSU3EohTBDDkYyVAFAGro+ltp9zqTs4ZLi5SaPHUKLeKLDe+YyfxFAGrQAUAFABQAUAZuv6VHrWntZ3Bja3YkvFLCsqSfKdu5W7K21xgg5Qc9aAKcfhq1WzFisdnBpzO0ktvZ2otxI5ACnIOVK4yCuGyqEMNuCAa1hFPDaRx3dz9qmXIMxQIXGeCwHG7GMkAAnJAUHAALFABQAUAFABQAUAZfiDVodIsJbm4cIiLuY+g9vcngVE5qCuzOrVjRg6k3ojyS6+J1/JdE2tnEIs8CRmLkfgQBXnSxsr6LQ+bnn9Tm9yCt57nf+DPFi6wksVzFJb3Vudk8Mg+aM/zxx35FdlGuqi8z2sFjY4qL0tJbo6n7XB/z0H5Gui53B9rg/56Ci4B9rg/56Ci4B9rg/56Ci4B9rg/56Ci4Gdr+uW2labNdyP+7jGSQMn0AHuScVE6ihHmZlXrRoQdSeyPJp/ibqcl1/o1pB5ZbCq5ZnP4gjn8K8542beiPm5Z9WcvcgrfO/5noXgrxYmtwSLMjwXEJ2TRP1Q/4cGu2jXVReZ7mCxscXF6Wkt0dR9rg/56Ct7naH2uD/noKLoA+1wf89BRdAH2uD/noKLoA+1wf89BRdAV7u9AULA2SerelK4GeZHJyXbPrmkMvWF2xcRynOehNNMRo0wCgAoAKACgAoA8++MME0vh2UxAlUdHfA/hBP8AUg1y4xN03Y8vOYylhXbuil4U/wCEG0vw3Z+IiC15ZqUeOVg0jTnBGF6E8fKegByeRxz0vYxgp9UcGE+o0qEcQ9199/T8jjbi51rxp4kv9S0uIWshVQwhk8vC9FDNxuPHX2rFe0rzcoaHDF4nH1pVKHu+jt/w5N/wjPjD/n4n/wDAz/69aewr9/xN/wCz8x/mf/gQv/CM+Mf+fif/AMDP/r0ewr9/xD+z8x/nf/gQf8Iz4x/5+J//AAMP+NHsK/f8Q/s/Mf53/wCBB/wjHjH/AJ+J/wDwM/8Ar0ewr9/xD+z8x/mf/gQxvC/jDeD5k5P977Z/9lS9hX7/AIi/s/ML/E//AAIp65oHiS106SfU3lltY8FgbjfjnGcZ96ipRqxjeT0MMVg8bTpudV3ivO53/h+TwLofh+z8RwIZLuGPyhHI+6Vpup+XoG/2ugFbw9jCCqdT0cO8BQoxxKWq++/p3MfwPqF1r/jDV9amgjhSWIKVjGFByuB7nCnJow0nUqSmGU1J4nE1K7VlY9AxXefRBigAxQAYoAMUAFABQBJbAm4jx/eFAG7VCCgAoAKACgAoAr31pHeQNFKoZSCMEZBB6gj0pNX0Ymk1Znn138LtNluTJH9piUnJSOUbfwyCRXJLBQbueRPJMNKXMrryX/DHXeHPD1podqsNrEEUHOM5JPqT3NdFOlGmrRPRw+Hp4eHJTVkbdaG4UAFABQAUAVr60jvIWjlUEEEEEZBB6gj0pNXE0mrM4K5+GGly3JkVJ41JzsjlG39RmuR4KDdzyZ5JhpS5tV5I67w/oVro1skNrEkaLyFX19Se5rphTUFZHpUaEKEOSmrI2Ks1CgAoAKACgCvd2wuFHOHHQ0WAzzYz5xtB981NguXbO08k73IL+3QU0gLdMAoAKACgAoAKAM/XdTTSdP8AtMihi0sVvGpJAaSWRYowSASAXdcnBwMnBoAdNqAha8DwysbW3W4ZYlLs2d/yqo5Y/IcDGTkUAY2m+Jp5b3SrPUdP+zXN55yNsaXy0dC+ArSxR78rE5wBuxhgrJmRQDp6ACgAoAKACgCG7meCHfHby3DblXy4ioOCwBb5iBgAljznAOATgEAyLTxLb3lit3bWd/LDLBHcW2yHcbhHhMqkYPyZCsv7zZ8wA/iXIBNpuv2mpahLaWgd2haRJHJUKrp5e5AN25iPNAJUFVIZWIYYoA16ACgAoAKACgAoAKACgAoAKACgAoAKACgBCAeoB+tAEL2ls8MkT28LRSRiF0MYKsgyNpHdeTx05NADRp9kJY5RZ23mxuZEfyl3K535YHHBPmSc/wC23qaALNABQAUAFABQBX1Cziv7KW1uGmWKQYYwzPC45zw6EMp46gigCs+jWJt4oIontoIoxDHHaTPbqqBGRVAjK4AV2wOx2kYKqQAO0vSLPS1VbJJEURiPDTO4+8zFjuJy7MzFn+85wWJwMAF+gAoAKACgAoAKACgAoAKACgAoAKACgAoAQnG0erBfzOP60MCb7Fcf8/EX/fo//FUrhYPsNx/z8Rf9+j/8VRcLB9huP+fiL/v0f/iqLhYPsNx/z8Rf9+j/APFUXCwfYbj/AJ+Iv+/R/wDiqLhYPsNx/wA/EX/fo/8AxVFwsH2G4/5+Iv8Av0f/AIqi4WD7Fcf8/EX/AH6P/wAVRcLB9iuP+fiL/v0f/iqLhYPsVx/z8Rf9+j/8VRcLB9huP+fiL/v0f/iqLhYPsNx/z8Rf9+j/APFUXCwfYbj/AJ+Iv+/R/wDiqLhYPsNx/wA/EX/fo/8AxVFwsH2G4/5+Iv8Av0f/AIqi4WD7Dcf8/EX/AH6P/wAVRcLB9huP+fiL/v0f/iqLhYhU53D+6xX8iR/SmAtABQAUAFABQAUANb70f/XRP/QhQwNepGFABQAUAFABQByfxSvtV0/wVeSeH7e6udUZ4kihtondnBkXzBlOVBQONwKkdiGxQB5F4h0vxD4r+B4sb3SNSTVtMu4GhsjbPGyxhfJXaCh8zgsxAYbck5AAQgGn8KtO8SWPw7vLDXYdVB+0XEf2S7tTIq2wtlwFDROcNIwXaFbIL4Q4agDcvvD+fAepWGh2+sQi3uXuoWsbRLGSSRIQULR+VDn59p+RXOYxhgQFUA9B8JWDaZpJtT9vwsruGvpEklbcdxJZCQeSfQ/zIBs0AFABQAUAFABQBkL96T/ro/8A6EapCHUAFABQA7Y/91vyoANj/wB1vyoANj/3W/KgBDG5K/K3DK3T0IP9KGBZ+2N/z6XH5L/jUhcPtjf8+lx+S/8AxVAXD7Y3/Ppcfkv/AMVQFw+2N/z6XH5L/wDFUBcPtjf8+lx+S/8AxVAXD7Y3/Ppcfkv/AMVQFw+2N/z6XH5L/wDFUBcPtjf8+lx+S/8AxVAXD7Y3/Ppcfkv/AMVQFw+2N/z6XH5L/wDFUBcPtjf8+lx+S/8AxVAXD7Y3/Ppcfkv/AMVQFw+2N/z6XH5L/wDFUBcPtjf8+lx+S/8AxVAXD7Y3/Ppcfkv/AMVQFw+2N/z6XH5L/wDFUBcPtjf8+lx+S/40BcrCNwW+U8szdPUk/wBapALsf+635UAGx/7rflQAbH/ut+VAH5zYr6A8gMUAGKALNhZteSOiPFHtQuWkJAwOvQH6/hW1Gg6zaTStrqZVaqpJNpv0EltcI0kLiWJcZYDBGfUdueP/ANYypUtOaLuv6/4b/h0NVNeV6P8Ar+v+GZXxWRoGKADFABigAxQAYoAVQpYbsgeoGaAH7Yf77/8AfA/xpahcYwUMduSPUjFMBMUAGKADFABigAxQAYoA6Hwz4cbWrwW6MvnCEzPGzmLauQF5KtnOc8DGCOcnA5q1dU1c2p0+d2MvVNOewk2swba7RPxgrImA6/gSMEcEEdDkDaEuZGco2KWKskMUAGKACgAoAKALdi0YW5SSRY/Mi2gsCQTvU44B7A1vQcfejJ2uv1T6ehjWUrxcVez/AEaAtEYJGXy0dkC+Wu7OdwOecjoPWlJxceZaeWvf+uo0pKVnr56f1+BUrE1CgAoAKACgAoAs6fIsV3G73E9sozmWBdzrweg3L9Oo61M1dbXGnZmx9vt/+hh13/vwP/j9Y8j/AJV9/wDwDTnX8z/r5mPqEiy3kjpcT3KnGJZ12u3A6jc306npW8VZbWM27srUxBQAUAFABQAUAd74A13SrC+nv9WvJILryEtVTyiyMihQD8oJzhFGD7nJzgcWJpTmlGCudNGcYu8mctr0lo17KthPLdRGV5PtEybXctjrzz0znAJJPHSuikpW95WMZtN6GXWpAUAFABQAUAFAEsHlEgTllUnllXJAwfcD0rSHJtIiXN9kkQw/Z3Rj+8OSG7DHbpnnn8x0xzV4cjT3/ry/r5Evm5k1t/Xn/XzK1YmoUAFABQAUAFDA6BLrSE0RxHEBfi4V1DRhhgK3rn5fu/KSfm9q5nGo5eX/AATVOPL5lfTJtPhvVLuCCN5mntg4QhWygjyQwJKgE4xjOBVSU3HQScbl7T9S01Zro3sUD+YxKOtskYAVW2qV2NgMcZxgjjk84JRlZApRuzma3MwoAKACgAoA19NmsV09lu2/eJN5qoIFLMAhAAcg9W25DDAAzyeKxqKXMuUuLjbUp3C26eX5Lo4KI7Y3cNjDKcgd8njj39KXNfUTaLmlXVrBf29zciCZIVcvDLAArkg8DaDuOWON2BwBkDGJkpWaRScb3ZX1q5gu9Ree1git4nVMRRZ2qQoB7DuCfx71cE0rMmTTehQqyQoAxv7Un/ux/kf8a8r69U7I7/qsO7D+1J/7sf5H/Gj69U7IPqsO7D+1J/7sf5H/ABo+vVOyD6rDuw/tSf8Aux/kf8aPr1Tsg+qw7sP7Un/ux/kf8aPr1Tsg+qw7sP7Un/ux/kf8aPr1Tsg+qw7sP7Un/ux/kf8AGj69U7IPqsO7D+1J/wC7H+R/xo+vVOyD6rDuw/tSf+7H+R/xo+vVOyD6rDuw/tSf+7H+R/xo+vVOyD6rDuw/tSf+7H+R/wAaPr1Tsg+qw7sP7Un/ALsf5H/Gj69U7IPqsPMP7Un/ALsf5H/Gj69U7IPqsO7D+1J/7sf5H/Gj69U7IPqsO7D+1J/7sf5H/Gj69U7L+vmH1WHdh/ak/wDdj/I/40fXqnZB9Vh3Yf2pP/dj/I/40fXqnZB9Vh3Yf2pP/dj/ACP+NH16p2QfVYd2H9qT/wB2P8j/AI0fXqnZB9Vh3Yf2pP8A3Y/yP+NH16p2QfVYd2H9qT/3Y/yP+NH16p2X9fMPqsPMP7Un/ux/kf8AGj69U7L+vmH1WHmH9qT/AN2P8j/jR9eqdkH1WHdh/ak/92P8j/jR9eqdkH1WHdh/ak/92P8AI/40fXqnZB9Vh3Z//9k=";
	
    private Log logger = LogFactory.getLog(this.getClass());  
    private static final String pattern = "yyyy-MM-dd HH:mm:ss:SSS";  
    private HttpClient httpClient = null;  
    private HttpPost method = null;  
    private long startTime = 0L;  
    private long endTime = 0L;  
    private int status = 0;  
    
    /** 
     * 接口地址 
     *  
     * @param url 
     */  
    public APIHttpClient(String url) {  
  
        if (url != null) {  
            this.apiURL = url;  
        }  
        if (apiURL != null) {  
            httpClient = new DefaultHttpClient();  
            method = new HttpPost(apiURL);  
  
        }  
    }  
  
    /** 
     * 调用 API 
     *  
     * @param parameters 
     * @return 
     */  
    public String post(String parameters) {  
        String body = null;  
        logger.info("parameters:" + parameters);  
  
        if (method != null & parameters != null  
                && !"".equals(parameters.trim())) {  
            try {  
  
                // 建立一个NameValuePair数组，用于存储欲传送的参数  
                method.addHeader("Content-type","application/json; charset=utf-8");  
                method.setHeader("Accept", "application/json");  
                method.setEntity(new StringEntity(parameters,"utf-8"));  
                startTime = System.currentTimeMillis();  
                HttpResponse response = httpClient.execute(method);  
                  
                endTime = System.currentTimeMillis();  
                int statusCode = response.getStatusLine().getStatusCode();  
                  
                logger.info("statusCode:" + statusCode);  
                logger.info("调用API 花费时间(单位：毫秒)：" + (endTime - startTime));  
                if (statusCode != HttpStatus.SC_OK) {  
                    logger.error("Method failed:" + response.getStatusLine());  
                    status = 1;  
                }  
  
                // Read the response body  
                body = EntityUtils.toString(response.getEntity());  
  
            } catch (IOException e) {  
                // 网络错误  
                status = 3;  
            } finally {  
                logger.info("调用接口状态：" + status);  
            }  
  
        }  
        return body;  
    }  
  
    public static void main(String[] args) {  
    	//线路站点信息测试
    	//apiURL += "&serveType=2&cityId=520100";
      	
      	//线路站点信息地址
    	//apiURL += "&serveType=2&cityId=520100&stationId=4028b8815c380cd3015c3823b3f00023&userId=4028820d5beb2c65015beb3fa78b0185";
    	
        APIHttpClient ac = new APIHttpClient(apiURL);  
        JsonArray arry = new JsonArray();  
        JsonObject j = new JsonObject();  
        //登录接口
//        j.addProperty("mobile", "15527916902");  
//        j.addProperty("code", "1325");  
//        arry.add(j);  
        
        //接送机订单测试
//        j.addProperty("orderType", "2");  
//        j.addProperty("orderStartingStationId", "4028b8815c380cd3015c3829c1530033");  
//        j.addProperty("orderTerminusStationId", "4028b8815c380cd3015c3823b3f00023");  
//        j.addProperty("orderStartime", "2017-05-28 16:12:00");  
//        j.addProperty("orderExpectedarrival", "2017-05-28 18:12:00");  
//        j.addProperty("orderUnitprice", "20");  
//        j.addProperty("orderNumbers", "2");  
//        j.addProperty("orderContactsname", "张三");  
//        j.addProperty("orderContactsmobile", "15527987654");  
//        j.addProperty("orderFlightnumber", "Z-15");
//        j.addProperty("orderStatus", "1");
//        j.addProperty("orderTrainnumber", "");  
//        j.addProperty("orderPaytype", "1"); 
//        j.addProperty("orderPaystatus", "0");  
//        j.addProperty("orderTerminusStationName", "龙里车站"); 
//        j.addProperty("orderStartingStationName", "一龙洞堡机场"); 
//        j.addProperty("userId", "4028820d5beb2c65015beb3fa78b0185"); 
//        j.addProperty("orderTotalPrice", "40"); 
//        j.addProperty("cityName", "贵阳市"); 
//        j.addProperty("cityId", "520100"); 
      	
        //取消订单
//		j.addProperty("orderId", "4028b8815c26b59b015c26c92ae80010"); 
//		j.addProperty("token", ""); 
        
        //用户意见反馈
//		j.addProperty("userId", "4028b8815c26b59b015c26c92ae80010"); 
//		j.addProperty("token", "111"); 
//		j.addProperty("content", "测试用户反馈信息"); 
        
        //修改用户信息
//        j.addProperty("token", "test");
//        j.addProperty("header", strImg);
//        j.addProperty("userId", "4028820d5beb2c65015beb3fa78b0185");
//        j.addProperty("imgName", "中文名称.png");
//        j.addProperty("cardNumber", "420984199302021025");
//        j.addProperty("address", "软件园E1栋");
        
        //获取用户个人信息
      j.addProperty("token", "test");
      j.addProperty("userId", "4028820d5beb2c65015beb3fa78b0185");
        
        // 返回Base64编码过的字节数组字符串
        System.out.println(ac.post(Base64Util.getBase64(j.toString())));  
    }  
  
    /** 
     * 0.成功 1.执行方法失败 2.协议错误 3.网络错误 
     *  
     * @return the status 
     */  
    public int getStatus() {  
        return status;  
    }  
  
    /** 
     * @param status 
     *            the status to set 
     */  
    public void setStatus(int status) {  
        this.status = status;  
    }  
  
    /** 
     * @return the startTime 
     */  
    public long getStartTime() {  
        return startTime;  
    }  
  
    /** 
     * @return the endTime 
     */  
    public long getEndTime() {  
        return endTime;  
    }  
}  