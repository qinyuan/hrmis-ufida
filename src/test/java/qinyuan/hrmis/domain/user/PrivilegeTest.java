package qinyuan.hrmis.domain.user;

import static org.junit.Assert.*;

import org.junit.Test;

import qinyuan.hrmis.domain.user.Privilege;

public class PrivilegeTest {

	@Test
	public void test() {
		Privilege privilege = new Privilege("pri_test");
		assertEquals("测试标题", privilege.getTitle());

		String[] anchors=privilege.getAnchors();
		assertEquals(3, anchors.length);
		
		String[] a=new String[3];
		a[0]="<a href='/hrmis/base-test/details1.jsp'>测试细节1</a>";
		a[1]="<a href='/hrmis/base-test/details2.jsp'>测试细节2</a>";
		a[2]="<a href='/hrmis/base-test/details3.jsp'>测试细节3</a>";
		
		for(int i=0;i<a.length;i++){
			assertEquals(a[i], anchors[i]);
		}
	}
	
	@Test
	public void test2() {
		Privilege privilege = new Privilege("pri_test2");
		assertEquals("测试标题", privilege.getTitle());

		String[] anchors=privilege.getAnchors();
		assertEquals(3, anchors.length);
		
		String[] a=new String[3];
		a[0]="<a href='/details1.jsp'>测试细节1</a>";
		a[1]="<a href='details2.jsp'>测试细节2</a>";
		a[2]="<a href='details3.jsp'>测试细节3</a>";
		
		for(int i=0;i<a.length;i++){
			assertEquals(a[i], anchors[i]);
		}
	}
}
