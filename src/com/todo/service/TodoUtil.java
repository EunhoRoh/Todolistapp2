package com.todo.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;


import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil { //��ü�� ���� �ʿ���� Ŭ���� �޼ҵ� ��밡�� static
	
	public static void createItem(TodoList l) {
		
		String title, category, due_date, desc;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[항목 추가] "+ "제목! ");
		title = sc.next();
		sc.nextLine();
		
		if (l.isDuplicate(title)) {
			System.out.println("제목이 중복된당!");
			return;
		}
		
		System.out.print("카테고리 "+ "입력! ");
		category = sc.next();
		sc.nextLine();//���͸� �Է¹޾ƾ� ���� ���� �Է� ���� ������ ���Ե� ���� ��
		
		System.out.print("내용 입력! ");
		desc = sc.next().trim();//trim�� �¿� ���� �������� Ȥ�ö� ����
		sc.nextLine();
	
		
		System.out.print("마감일자 입력! ");
		due_date = sc.next().trim();//trim�� �¿� ���� �������� Ȥ�ö� ����
		
		
		
		TodoItem t = new TodoItem(title, desc, category, due_date);
		if(l.addItem(t)>0)
			System.out.println("추가되었음.");
		
	}

	/*
	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
			
		System.out.println("[Index Delete]\n" + "Insert you want to delete! ");
		
		int index = sc.nextInt();
		
		int count =0;
		char y_n;
		for (TodoItem item : l.getList()) {
			count++;
			
			if(index == count) {
				System.out.println(item.toString());
				System.out.println("Are you gonna remove it? (y/n) > ");
				y_n = sc.next().charAt(0);
				if(y_n=='y') {
					l.deleteItem(item);
					System.out.println("Delete Complete!");
				}
				else if(y_n=='n') {
					System.out.println("Delete Cancel!");
				}
				
				break;
			}
		
		}
	}*/
	public static void deleteItem(TodoList l) {
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[항목 삭제]\n"
				+"삭제할 항목의 번호를 입력해랑! ");
		int index = sc.nextInt();
		if(l.deleteItem(index)>0)
			System.out.println("삭제되었습니다.");
	}
	

	/*
	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("[Index Edit]\n" + "Insert you want to Edit! ");
		int index = sc.nextInt();
		System.out.println(l.getList().get(index-1));
		
		
		if (!l.isDuplicate(title)) {
			System.out.println("No title!");
			return;
		}*/
		
		/*
		System.out.println("Insert New Title! ");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("Duplicate!");
			return;
		}
		sc.nextLine();
		
		System.out.println("Insert New Category! ");
		String new_Category = sc.next().trim();		
		sc.nextLine();

		
		
		System.out.println("Insert Description! ");
		String new_description = sc.next().trim();
		sc.nextLine();
		
		System.out.println("Insert Due_Date! ");
		String new_due_date = sc.next().trim();
		
		int count=0;
		
		for (TodoItem item : l.getList()) {
			
			if ((index-1) == count) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_Category, new_title, new_description, new_due_date);
				l.addItem(t); //�����ð����� ����Ÿ �ٽ� �¾���
				System.out.println("Edit Complete!");
				break;
			}
			count++;
		}

	}*/
	public static void updateItem(TodoList l) {
		
		String new_title, new_desc, new_category, new_due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[항목 수정]\n" + "수정할 항목의 번호를 입력! ");
		int index = sc.nextInt();
	
		
		System.out.print("새 제목! ");
		new_title = sc.next().trim();
		sc.nextLine();
		
		System.out.print("새 카테고리! ");
		new_category = sc.next();		
		sc.nextLine();

		System.out.print("새 내용! ");
		new_desc = sc.next().trim();
		sc.nextLine();
		
		System.out.print("새 마감일자! ");
		new_due_date = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(new_title, new_desc,new_category, new_due_date);
		t.setId(index);
		if(l.updateItem(t)>0)
			System.out.println("수정되었습니다.");
	}
	
	/*
	public static void completeItem(TodoList l, String index) { //todoUtil에서는 바꿀 index의 list를 찾아서 t로 보내준다.
		String new_title, new_desc, new_category, new_due_date;
		
	
	
		TodoItem t = l.getList(index);

		if(l.completeItem(t)>0)
			System.out.println("완료 체크하였습니다.");
		
	}*/
	
	
	public static void saveList(TodoList l, String filename) {
		// TODO Auto-generated method stub
		try {
			Writer w = new FileWriter(filename);
		    
		    for (TodoItem myitem : l.getList()) {
				
				//w.write(myitem.getTitle()+"##"+myitem.getDesc()+"##"+myitem.getCurrent_date()+"\n");
		    	w.write(myitem.toSaveString());
			}
			w.close();	
			System.out.println("Data Saved.");
		} catch (FileNotFoundException e) {
			// TODO: handle exception
			
			e.printStackTrace();
		}catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}

	
	public static void loadList(TodoList l, String filename) {
		// TODO Auto-generated method stub
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String oneline;
	
			while((oneline = br.readLine())!= null) {
				
				StringTokenizer st = new StringTokenizer(oneline, "##");
				String category =st.nextToken();
				String title = st.nextToken();
				String desc = st.nextToken();
				String due_date=st.nextToken();
				String current_date = st.nextToken();
				TodoItem t = new TodoItem(category, title, desc, due_date, current_date);
				//t.setCurrent_date(current_date);
				//l.addItem(t);
				l.addItem(t);
			
	
			}
			br.close();
			System.out.println(l.getList().size() + " Data loaded!!!");
		} catch (FileNotFoundException e) {
			// TODO: handle exception
			System.out.println(filename+" is gone.");
			
			
		}catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
	/*
	public static void findList(TodoList l, String find) {
		//Set<String> clist = new HashSet<String>();
		Scanner sc = new Scanner(System.in);
	
		int count =0;
		int find_count=0;
		//char y_n;
		for (TodoItem item : l.getList()) {
			count++;
			if(item.getTitle().contains(find) || item.getDesc().contains(find)) {
				System.out.println(count+". "+item.toString());
				find_count++;
			}
			
			
		}
		System.out.println("총 "+find_count+ "개의 항목을 찾았습니다.");
		
	}*/
	public static void findList(TodoList l, String keyword) {

		int count =0;

		for (TodoItem item : l.getList(keyword)) {
			System.out.println(item.toString());		
			count++;
		}
		System.out.printf("총 %d개의 항목을 찾았습니다.\n", count);
		
	}
	
	
	
	
	public static void findCateList (TodoList l, String cate) {
		int count =0;
		for (TodoItem item : l.getListCategory(cate)) {
			System.out.println(item.toString());
			count++;	
		}
		System.out.printf("\n총 %d개의 항목을 찾았습니다.\n", count);
	}
	
	
	
	public static void listCateAll(TodoList l) {
		int count =0;
		
		for (String item : l.getCategories()) {
			System.out.print(item+" ");
			count++;
		}
		System.out.printf("\n총 %d개의 카테고리가 등록되어 있습니다.\n", count);
		
	}
	
	public static boolean isExistCategory(List<String> clist, String cate) {
		for(String c: clist) {
			if(c.equals(cate))
				return true;

		}
		return false;
	}
	/*
	public static void listAll(TodoList l) {
		int num = l.getList().size();
		
		System.out.println("[All List, Num of List : "+num+"]");
		int i=0;
		for (TodoItem item : l.getList()) {
			i++;
			System.out.println(i+". "+item.toString());
		}
	}*/
	public static void listAll(TodoList l) {
		System.out.printf("[전체 목록, 총 %d개]\n", l.getCount());
		for (TodoItem item : l.getList()) {
			System.out.println(item.toString());
		}
	}
	
	
	/*
	public static void listAll(TodoList l, int index_completed) { // complete된 index를 찾아서 getlist에 보내준다. //아니면 1과 같은 index를 모두 뽑으라는 건가
		//System.out.printf("[전체 목록, 총 %d개]\n", num_index_completed);
		
		int count=0;
		for (TodoItem item : l.getList(index_completed)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("총 %d개의 항목이 완료되었습니다.\n", count);
	}*/
	
	public static void listAll(TodoList l, String orderby, int ordering) {
		System.out.printf("[전체 목록, 총 %d개]\n", l.getCount());
		for (TodoItem item : l.getOrderedList(orderby, ordering)) {
			System.out.println(item.toString());
		}
	}
	
}
