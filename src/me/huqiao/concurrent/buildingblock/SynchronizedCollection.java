package me.huqiao.concurrent.buildingblock;

import java.util.Vector;

public class SynchronizedCollection {
	
	//����ͬ����ԭ�Ӳ�������һ��Ͳ��پ���ԭ������
	public void getLast(Vector vector) {
		//�ͻ�����ʽ����֤���ϲ�����ͬ��
		synchronized (vector) {
			int size = vector.size();
			vector.get(size);
		}
	}
}
