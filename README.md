# 项目内容

对student的数据进行查找、插入、删除、更新

- GET（SELECT）：从服务器取出资源（一项或多项）。
- POST（CREATE）：在服务器新建一个资源。
- PUT（UPDATE）：在服务器更新资源（客户端提供改变后的完整资源）。
- DELETE（DELETE）：从服务器删除资源。

# 项目结构

![img](https://cdn.nlark.com/yuque/0/2024/png/26411187/1713671184591-1eaa108f-8494-406e-8d36-cc3f69e033e3.png)

编写代码是从底层往上编写

# 创建表

![img](https://cdn.nlark.com/yuque/0/2024/png/26411187/1713682478000-c27d690a-0eb1-44b4-8631-d3225c842a6a.png)

打开终端，输入：mysql -u root -p，进入mysql的命令行

然后：(注意；意味着结束了)

建库语句：

```sql
CREATE DATABASE test
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci;
```

建表语句：

```sql
CREATE TABLE student (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  email VARCHAR(100) NOT NULL,
  age INT
);
```

其中AUTO_INCREMENT PRIMARY KEY代表id是自增的，在ide的代码中需要体现出来。

## 添加数据

在终端输入:

```sql 
insert into student （name，email，age）values （'name','s223',18);
```

ps：数据随便输入，注意不要忘记分号

然后可以输入:

```sql
select * from student；
```

来查表，看看输入是否成功。

## 在ide中引入数据库

最后在 application.properties引入数据库：

数据库的url要根据实际情况修改

```java
spring.application.name=demo
spring.datasource.url=jdbc:mysql://localhost:3306/test
spring.datasource.username=root
spring.datasource.password=
```

# 步骤

## 1.下载项目框架

访问[Spring Initializr](https://start.spring.io/)

选择如图：

![image-20250412210947675](assets\images\image-20250412210947675.png)

名字可以改，然后把文件解压，移动到自己的代码文件下

用ide打开项目

## 2.数据层：建data软件包

### 建StudentRepository(学生仓库)接口

```java
@Repository//使用这个注解代表是数据层的代码，把下面的类当成一个springbean，放在spring容器中去管理它的依赖关系
public interface StudentRepository extends JpaRepository<Student,Long> {
    //extends JpaRepository代表继承父类接口，JpaRepository里的方法可以实现我们的业务逻辑
    //<Student,Long>是对应着同一个包下的student类，它的数据类型是long
}
```

### 建Student类（映射着数据库中的数据）

```java
@Entity//表示是映射到数据库表的一个对象
@Table(name = "student")//表的名字叫student
public class Student {
    //以下是表中有的数据
    @Id//表示是自增的
    @Column(name = "id")//注解表示把数据库的东西映射到Java中
    @GeneratedValue(strategy = IDENTITY)//表明id这个数据是自增数据，是由数据库自动生成的
    private long id;//数据类型是long

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "age")
    private int age;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
```

记得生成**Getter and Setter**方法：鼠标右键，点击生成，点击**Getter and Setter**，然后**按住 `Shift` 键** → 点击 **第一个字段** → 再点击 **最后一个字段**，全选所有字段，最后点击生成

## 3.服务层：建service软件包

### 建StudentService接口

在这里写最基本需要实现的方法

例如：

```java
public interface StudentService {
    //要实现的方法
    Student getStudentById(long id);//查询id
}
```

在接口处写了一个方法，就要到StudentServiceImpl类里写对应的方法

### 建StudentServiceImpl类

要跟数据层有连接，所以使用：

```java
@Autowired//表示把StudentRepository注入进来
private StudentRepository studentRepository;
```

在接口处写了一个方法后，会发现StudentServiceImpl类这里有红色波浪线，点击，生成含@Override的方法，然后对这个方法进行修改：

例如查找数据：

```java
@Override
public Student getStudentById(long id) {
   return studentRepository.findById(id).orElseThrow(RuntimeException::new);//返回结果或者抛出异常
}
```

## 4.控制层：建controller包

### 建StudentController类

先加上注解：（类外面）

```java
@RestController//表示是个Controller
```

然后在类里面加上以下代码，与服务层连接

```java
@Autowired
private StudentService studentService;//把service层的数据导进来
```

最后写需要实现的方法，例如查找数据：

```java
@GetMapping("/student/{id}")//查询接口
public Student getStudentById(@PathVariable long id){
    return studentService.getStudentById(id);
}
```

在浏览器输入生成的网站，就能看到查的id里对应的数据

## 如果有些敏感数据不想返回给前端看呢？

那就建立一个可以给前端看的数据，让服务层，控制层都引用这个可以看的数据。

这个跟数据层是一样的

### 1.建dto包—>建StudentDTO类

例如我不想给前端看到年龄的数据，那这个类的代码如下：

```java
package com.example.demo.dto;
//后端有些加密数据不想给前端看到，所以建这个软件包
public class StudentDTO {
    private long id;
    private String name;
    private String email;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
```

记得生成**Getter and Setter**方法

### 2.在服务层修改成StudentDTO对象

在StudentService接口：

```java
import com.example.demo.dto.StudentDTO;

public interface StudentService {
    //要实现的方法
    StudentDTO getStudentById(long id);//查询id
    }
```

在StudentServiceImpl类：

```java
@Override
public StudentDTO getStudentById(long id) {
   Student student=studentRepository.findById(id).orElseThrow(RuntimeException::new);//返回结果或者抛出异常
    //把student对象转换成studentdto对象,使用converter
    return StudentConverter.convertStudent(student);

}
```

这里发现，需要把student对象转换成studentdto对象,所以创建了converter

### 3.建converter包—>建StudentConverter类

这个方法可以把student对象转换成studentdto对象

```java
public class StudentConverter {
    public static StudentDTO convertStudent(Student student){
        StudentDTO studentDTO=new StudentDTO();
        studentDTO.setId(student.getId());
        studentDTO.setName(student.getName());
        studentDTO.setEmail(student.getEmail());
        return studentDTO;
    }
```

### 4.在控制层修改成StudentDTO对象

```java
@GetMapping("/student/{id}")//查询接口
public StudentDTO getStudentById(@PathVariable long id){
    return studentService.getStudentById(id);
}
```

## 前端需要知道接口请求是否正常

### 1.建Response类

代码如下：

```java
package com.example.demo;
//前后端的格式
public class Response<T> {//泛型
    private T data;
    private boolean success;//是否成功
    private String errorMsg;//报错


    //封装
    //成功了
public static <K> Response<K> newSuccess(K data){
    Response<K> response=new Response<>();
    response.setData(data);
    response.setSuccess(true);
    return response;
}

//失败了
public   static Response<Void>newFail(String errorMsg){
    Response<Void>response=new Response<>();
    response.setErrorMsg(errorMsg);
    response.setSuccess(false);
    return response;
}

//Getter and Setter方法
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
```

### 2.在控制层加上Response

```java
@GetMapping("/student/{id}")//查询接口
public Response<StudentDTO> getStudentById(@PathVariable long id){
    return Response.newSuccess(studentService.getStudentById(id));
}
```

这样访问网站，返回的结果就会显示是否成功了

## 增加：增加，删除，更新功能

这里的代码是从上往下写

### 1.在控制层增加代码

```java
@PostMapping("/student")//增加
public Response<Long> addNewStudent(@RequestBody StudentDTO studentDTO){
    return Response.newSuccess(studentService.addNewStudent(studentDTO));
}

@DeleteMapping("/student/{id}")//删除
public void deleteStudentById(@PathVariable long id){
    studentService.deleteStudentById(id);
}

@PutMapping("/student/{id}")//更新
public Response<StudentDTO>updateStudentById(@PathVariable long id,@RequestParam(required = false)String name,@RequestParam(required = false)String email){
    return Response.newSuccess(studentService.updateStudentById(id,name,email));
}
```

addNewStudent、deleteStudentById、updateStudentById会标红，点击就可以到服务层增加代码

### 2.在服务层增加代码

#### 在StudentService接口增加

点击红色的addNewStudent、deleteStudentById、updateStudentById会跳到接口增加，检查数据类型是否有错就好

#### 在StudentServiceImpl类中增加

这里就要注意很多点，比如增加数据之前先要判断email是否重复了（保证Email唯一性）；在删除之前判断用户id是否存在；在更新之前要判断用户id是否存在，而且也要判断要更改的数据是否相同，如果不相同再更新。

```java
@Override
public Long addNewStudent(StudentDTO studentDTO) {
    List<Student>studentList=studentRepository.findByEmail(studentDTO.getEmail());//添加之前要确保email唯一
    if (!CollectionUtils.isEmpty(studentList)){//如果不为空，说明被占用了，需要抛出异常
        throw  new IllegalStateException("email:"+studentDTO.getEmail()+"has been taken");

    }
    //新增的话需要把StudentDTO转换为数据库的student，在converter添加方法
    Student student=studentRepository.save(StudentConverter.convertStudent(studentDTO));
    return student.getId();
}

@Override
public void deleteStudentById(long id) {
    //首先要判断用户id是否存在
    studentRepository.findById(id).orElseThrow(()->new IllegalArgumentException("id:"+id+"不存在"));
    studentRepository.deleteById(id);
}

@Override
@Transactional
public StudentDTO updateStudentById(long id, String name, String email) {
    //首先要判断用户id是否存在
    Student studentInDB=studentRepository.findById(id).orElseThrow(()->new IllegalArgumentException("id:"+id+"不存在"));
    if (StringUtils.hasLength(name)&&!studentInDB.getName().equals(name)){
        studentInDB.setName(name);
    }
    if (StringUtils.hasLength(email)&&!studentInDB.getEmail().equals(email)){
        studentInDB.setName(email);
    }
    Student student=studentRepository.save(studentInDB);//保存更改
    return StudentConverter.convertStudent(student);//返回更改结果给前端
}
```

ps：需要注意在实现增加方法时需要把StudentDTO转换为数据库的student，所以在converter类里添加方法：

```java
public static Student convertStudent(StudentDTO studentDTO){
    Student student=new Student();
    student.setName(studentDTO.getName());
    student.setEmail(studentDTO.getEmail());
    return student;
}
```

### 3.在数据层增加代码

在写StudentServiceImpl类中的代码时，例如findByEmail方法会变红，点击，然后在StudentRepository接口增加代码即可（注意判断数据类型是否正确）。有些方法不需要增加，因为StudentRepository接口继承了父类，父类的里面已经有可以实现的代码了。

### 4.使用postman软件验证功能是否实现成功

