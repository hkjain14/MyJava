package Interviews.Uber_Jan24;

public class R2 {
    /*


const employees = [];
class Employee {
	emp_id;
	name;
	emp_manager;
	role;
	email;
	reportees = [];

	addReportees(emp_id) {
		this.reportees.push(emp_id);
	}

	constructor(emp_id, name, emp_manager, role, email) {
		this.emp_id = emp_id;
		this.name = name;
		this.role = role;
		this.email = email;
		if(emp_manager) {
			const manager = employees.find((el) => el.name === emp_manager);
			this.emp_manager = manager.emp_id;
			manager.addReportees(emp_id);
		}
	}
}


function dfs_util(e, visited) {
	visited.push(e);
	console.log(e.name, " -> ");
	if(e.reportees.length>0) {

		e.reportees.forEach((el) => {
		const emp = employees.find((el2) => el2.id === el);
		if(!visited.includes(emp)) {
			dfs_util(el,visited);
		}
	});
	}

}


function printPaths(e1) {
	const visited = [];
	// console.log(e1.name);
	dfs_util(e1,visited);
	// console.log(visited.contains('1'));
}


function main() {
	const e1 = new Employee(1,"Dara",null, "CEO", "dara@uber.com");
	employees.push(e1);
	const e2 = new Employee(2,"Warner","Dara", "Business Planning", "warner@uber.com");
	employees.push(e2);
	console.log(e1.reportees);
	const e3 = new Employee(3,"Ankit","Dara", "City Lean", "ankit@uber.com");
	employees.push(e3);
	const e4 = new Employee(4,"Akhil","Ankit", "Central Supply Lead", "akhil@uber.com");
	employees.push(e4);
	const e5 = new Employee(5,"Abhishek","Akhil", "Supply Lead", "abhishek@uber.com");
	employees.push(e5);


	printPaths(e1);

	// outputIcs(e1);

}

main();
     */
}
