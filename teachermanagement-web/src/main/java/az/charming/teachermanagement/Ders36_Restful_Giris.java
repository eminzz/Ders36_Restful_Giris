/* Bir dene clas qururug controller de adini StudentEndpoint qoyurug. StudentRest, StudentController (bu artig var
evvelden) qoya bilerik adini.StudentControllerden index metodunu (bezi deyisikler ederek), studentService deyisenini
falan burda da yazirig, bu clasin uzerine @RestController yazirig, StudentController-de ise @Controller yazmisdig,
arada ferqler var , bu ferqlere de baxacagiq.Burda index metodunda string olarag sehifenin adini istifade etmirik ve
bize model lazim olmur, hemcinin addAttribute da sildik ve listi oldugu kimi return edirik (findAll-u) ve metodun
tipini List<StudentEntity edirik belelikle endpoint-i yazmis oldug -
@RestController
public class StudentEndpoint {
    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "students", method= {RequestMethod.GET})
    public List<StudentEntity> index(@RequestParam (required = false) String name,
                        @RequestParam(required = false) String surname,
                        @RequestParam(required = false) Integer age,
                        @RequestParam(required = false) BigDecimal scholarship
    ){
        return studentService.findAll(
                name,
                surname,
                age,
                scholarship
        );
    }
}
application-i run etdik error verdi sebeb kecen dersden qalan namedquery-ni gosterir umumiyyetle bizde
StudentRepository-deki butun queryleri commente alirig helelik, birde iki controller clasinda index uzerinde
controllerin muracidt edeceyi yeri yazmisig yeni "students" bunlari ayird etmek ucun StudentEndPoint-dekine
rest/students edirik ve StudentController clasinin uzerinde @RequestMapping("students") yazirig artig metodlarda
students yazmaga ehtiyac qalmir harda varsa silirik, qisacasi bu controllere gelmek ucun students yazmalisan , hemcinin
contorllerin metoduna gelmek ucun metodun adini yazmalisan. StudentEndPoint clasin uzerinde de hemcinin
@RequestMapping("/rest") yazirsan.Birde StudentControllerde index metodun uzerine yazdigimiz bunun -
@RequestMapping(method= {RequestMethod.GET}) qisa hali var silib o sekilde yazag yeni -  @GetMapping. Hemcinin
asagidaki add, update, delete metodlarin uzerindeki  @RequestMapping-i @PostMapping edirik RequestMethod-u da silirik.
Meselen bele  @PostMapping(value = "/add") olur. Run eledik proyekt ayaga qalxdi.

Workbench de student table-na bir obyekt elave etdik school_id ucunde school table-inda bir mekteb yazdig ve mektebi
bagladig student-e. Broweserde http://localhost:8080/rest/students yazirig bele bir sey gelir ekrana -

[{"id":1,"name":"Sarkhan","surname":"Rasullu","age":28,"scholarship":999.00,"teacherList":[],"school":
{"id":1,"name":"mekteb","studentList":[]}}]
Sag click edib ekranda baxirig network-e orda da preview-a baxirig goruruk ki hemen ekranda olan obyekt seliqeli
sekilde burda da gorsenir school_id ile baglandigi ucun school obyekti de bu student-in icindedi. Bu formata
json( javascript object notation) formati deyilir. [ ]- bu kvadrat massivi ifade edir {} bu da obyekti Burda da
gorsenir 0-ci element {} bununladir.School da studentlist-de massivle gosterilir. Bu bizim neyimize lazimdir? Bu bir
datadir biz bu datani vebsaytlarda istifade ede bilirik ve browser bunu avtomatik taniyir. neceki browserde javascript
obyekti yazirig eyni sekilde bunu da saytinda istifade et.Indi bunun istifadesine baxag, gelirik proyektde
students/index.html sehifesine bir script acirig (head-in icinde evvel acdigimiz kimi)-
<script>
  function showAllStudents(){
      var list = [
              {"id":1,
                 "name":"Sarkhan",
                 "surname":"Rasullu",
                 "age":28,
                 "scholarship":999.00,
                 "teacherList":[],
                 "school":{
                    "id":1,
                    "name":"mekteb",
                    "studentList":[]
                 }
              }
           ];
      var studentsPanelRest = document.getElementById("studentsPanelRest");
      for(var i=0; i<list.length; i++){
        studentsPanelRest.innerHTML = studentsPanelRest.innerHTML+"ad:"+list[i].name+", soyad:"+list[i].surname+"<br/>";
      }
  }
</script>

ve body icinde bir dene div acib id veririk normalda rest yazilmir id ucun ancag ferqlendirmek ucun yazirig -
</head>
<body>
<div  id= "studentsPanelRest">

</div>
gorunduyu kimi bir function(metod) acdig, javascriptimiz listine menimsetdik browserde olan massivi. Qeyd:
"Javascriptde massiv elan etmek ucun  var s=[] yazma kifayetdir artig massiv quruldu.Icinde {} yazdin olur obyekt,
id sitesen meselen id:1 oldu id. s[0] ={ }, belede yaza bilersen eyni java stilindedir sadece elan edende say verme
mecburiyyetin yoxdu javadaki kimi, genislene bilendir arda arda yazib artira bilirsen.
Innerhtml- asagida qeyd etidyimiz div ici demekdir. <br/> - html-de asagi dusmedi.Bu funksiyani cagiracayig ancag
asagida cagirirlar yazilan funksiyani sebebi normalda script html uzerinde isleyen seydir, duzdu javascript istifade
edirsen acang html uzeri isleyir ve html-de yuklenme yuxaridan asagiya gedir eger bu funksiyani ele yuxardaca koddan
sonra hemen script-in icinde cagirsaydig yeni div-den once , funksiya ise dusmeye baslayanda div axtaranda error
verecekdi cunki yuxardan asagiya dogru gelir, divin load olmasi gecikende artig cagirilan funksiya ise dusmus olur ve
belelikle div-i tapa bilmir.Oncun en asagida yazilir-
</body>
<script>
    showAllStudents();
</script>
</html>


Browsere daxil olurug yuxarida solda ad soyad falan gelib , inspect edib baxsag html-de gorerik yazdigimiz kodlari, bir
nece ad soyad olsa alt alta gelecek.Belelikle json ustunluyunu gorduk.Javascriptde json-u elimizle yazdig, ancag
browser( rest/students -de) bazadan cekerek verir json-u (bu dinamikdi real melumatdi).Biz indi bu url-i
(rest/students)-i html-e(bayag elimizle yazdigimiz yere) menimsetsek yeni orda olan obyektin eynisini menimsetsek dolayi
yolla ele bazadan cekdiyimizi ekranda gostermis olacayiq.Bu kodu google-da 'javascript send get request' yazib
stackflow-dan hazir gotururuk -
var xmlHttp = new XMLHttpRequest();
xmlHttp.open( "GET", theUrl, false ); // false for synchronous request
xmlHttp.send( null );
return xmlHttp.responseText;
bele bir koddu indi bayag html-de yazdigimiz (bayag acdigimiz scriptde yeni) obyekti silirik onun yerine bunu yazirig
tebii theUrl yerine oz urlmizi yazirig, xmlHttp.responseText-burdan bir cavab gelir ve bu cavabi return etmirik, var
list-e menimsedirik lakin bu cavab text olarag gelir onuncun Json.parse icerisine atanda texti obyekti kimi teqdim edir.
Yeni ki biri var bele yazasan "[]" - bu textdir Json.parse deyende ise cevirir []-buna.Yeni bize string gelir bizde bunu
jsona ceviririk. netice bu cur olur bayag acdigimiz scriptimiz-
<script>
  function showAllStudents(){
      var xmlHttp = new XMLHttpRequest();
      xmlHttp.open( "GET", "http://localhost:8080/rest/students", false ); // false for synchronous request
      xmlHttp.send( null );
      var list = JSON.parse(xmlHttp.responseText);
      var studentsPanelRest = document.getElementById("studentsPanelRest");
      for(var i=0; i<list.length; i++){
        studentsPanelRest.innerHTML = studentsPanelRest.innerHTML+"ad:"+list[i].name+", soyad:"+list[i].surname+"<br/>";
      }
  }
</script>
"Bu numuneden once muellim ajax ile olan numuneye baxdi lakin ajax jquery-de isleyir bize de javascriptde nece oldugunu
bilmek lazim oldugu ucun bu numuneni istifade etdik, ajax- arxada boyuk kodu bizim evezimizden icra etmek demekdir".
Yene browsere baxirig bayaq kimi ad soyad falan gelir inspect edib koda baxsaq gorerik ki, 2 dene students var biri
localhost:8080/students- den gelir, biri de localhost:8080/rest/students-den - buda bayag ki obyektdir.indi bazaya 2 ci
sexsi elave edib baxag browserde neticeye. workdbench-de student table-inda bir dene de telebe yazib school_id sinede
eyni mektebi verdik ve  browsere geldi.Bunun ustunlukleri nedir umumiyyetle: 1) frontend ile backend musteqil isleye
biler meselen Sebine restfulapi hazirlayib, Serxanda html-ni hazirlayib o restfulapini cagirir ve o apiden netice gelir
onu browserde cixardir.Bir dene de elavesi var meselen google-un apisi var bize bu api restful verir biz de bunun
vasitesile youtube-dan hansisa kanalin melumatlarin(videolarini, abunecilerini ve s) dartib oz saytimizda gostere
bilirik.Normalda biz youtube-la elaqe qura bilmirik ancag bu cur api verende yeni url-e muraciet edib bize json
obyektler ve s. verende biz muraciet ede bilirik. 2) diger ustunluyu- gelirik students/html-de body icinde duyme
qoyurug -
</head>
<body>
<button onclick="showAllStudents()">show all students</button>
funksiyani duyme ile cagiririg isteyende ve buna gore lap asagida yazdigimiz script-i silirik(bayag funksiyani cagirmag
ucun yazmisdig).Browserde students url-ne daxil olub baxasg yeniden gorerik ki inspect- networkde students oturur bu
ekranda olandi students url-inden gelen ve yuxari solda olan "show all students" duymesine basanda ise students gelir
(json obyektleri) rest/students -den.Yeni bu nedir? sehifenin olmasi ucun bu qeder html emele gelir , datalar oturur
ancag bize  obyektler lazim olanda tekce sehife basdan qurulmur sadece yenilenme obyektlerin oturmasi ucun olur.Adice
facebook-da asagi dusub scroll edende bir dene request gedir ve o requestden cavab gelir, ekrana cixir. Vebsaytda
sehifeleri quranda da bu cure fikirlesmek lazimdir ki bize hansi melumatlar birbasa lazimdir , hansi melumatlar sonradan
gelse de olar. Meselen saytin menyulari falan dinamik(birbasa) gelmelidir, hansi duymeye basanda ise is gorurse o da
rest ile yeni json ile olmalidir , bele daha suretlidir eks halda her defe sayt refresh verib yenilenmelidir.Indi biz
table-i silib javascript vasitesile load edeceyik. students/html-de body->div(id=main panel)->table->tbody-nin icindeki
tr-ni (tdler variydi Emin falan yazilmisdi, delete buton falan variydi evvelden) umumiyyetle buradan gotururuk ve bezi
deyisiklikler edib yuxarida function showAllStudents() metodunda yazacagiq , hazirda tbody bele veziyyete gelir-
<tbody id="tablerows">

</tbody>

Yuxaridaki scriptimiz (showall students) bele olur -

    <script>
        function showAllStudents(){
            var xmlHttp = new XMLHttpRequest();
            xmlHttp.open( "GET", "http://localhost:8080/rest/students", false ); // false for synchronous request
            xmlHttp.send( null );
            var list = JSON.parse(xmlHttp.responseText);
            var studentsPanelRest = document.getElementById("tablerows");
            var trStr= "<tr >\n" +
                "                <td >:id</td>\n" +
                "                <td >:name</td>\n" +
                "                <td >:surname</td>\n" +
                "                <td >:age</td>\n" +
                "                <td >:scholarship</td>\n" +
                "                <td >:schoolname</td>\n" +
                "                <td>:teachername</td>\n" +
                "                <td>\n" +
                "                    <form method=\"POST\" action=\"/students/delete\">\n" +
                "                        <input type=\"hidden\" name=\"id\" value=\":id\"/>\n" +
                "                        <button type=\"button\"\n" +
                "                                class=\"btn btn-danger\"\n" +
                "                                data-bs-toggle=\"modal\"\n" +
                "                                data-bs-target=\"#panelDelete\"\n" +
                "                                onclick=\"fillSelectedItemForDelete(:id)\"\n" +
                "                        >Delete</button>\n" +
                "                        <button type=\"button\"\n" +
                "                                class=\"btn btn-secondary\"\n" +
                "                                data-bs-toggle=\"modal\"\n" +
                "                                data-bs-target=\"#panelUpdate\"\n" +
                "                                onclick=\"fillSelectedItemForUpdate(\n" +
                "                                    :id,\n" +
                "                                    :name,\n" +
                "                                    :surname,\n" +
                "                                    :age,\n" +
                "                                    :scholarship\n" +
                "                                    )\">\n" +
                "                            Update</button>\n" +
                "                    </form>\n" +
                "                </td>\n" +
                "            </tr>"
            for(var i=0; i<list.length; i++){
                var teachers = "";
                for(var j=0; j<list[i].teacherList.length;j++){
                    teachers += list[i].teacherList [j].name+";";
                }
                studentsPanelRest.innerHTML = studentsPanelRest.innerHTML+
                    trStr.replace(":id",list[i].id)
                        .replace(":name",list[i].name)
                        .replace(":surname",list[i].surname)
                        .replace(":age",list[i].age)
                        .replace(":scholarship",list[i].scholarship)
                        .replace(":teachername", teachers)
                        .replace(":schoolname", list[i].school.name)
            }
        }
    </script>
body-e id verdik tablerows adli ve bunu getElementById metoduna verdik.var trStr-e yapisdirmisig goturduyumuz tr-ni,
burda html kimi yapisdirib eslinde ise stringdir. br yazmasagda olur zaten bu tr-dir, alt alta tr-tr olur table bu cur
olur onsuz. Asagida for-da trStr-e bir bir replace edirik id, name surname filan ve teachername ve schoolname-i de
artiririg. fillSelectedItemForUpdate metodunda ":id :name" bunlari iki noqte ile yazmagimizin sebebi odur ki eger bele
yazmasaydig asagida name-i replace edende yuxarida kod olan yeni burdaki name-i de replace edecekdi
<input type=\"hidden\" name=\"id\" value=\":id\"/>\n" Yeni bize kod terefin replace olmasi lazim deyil, esas :name
silinsin ad nedirse o otursun. Replace ele bunu edir :name sozunu tapir silir yerine listden otuzdurur
-replace(":name",list[i].name).Teachers-ler ucun for acirig ki her defe teacheri cekek bu dongunu ele replace yazdigimiz
dongude qururug -
var teachers = "";
for(var j=0; j<list[i].teacherList.length;j++){
     teachers += list[i].teacherList [j].name+";";
}
Workbench-de de 2 dene teacher yazirig teacher table-inda bos qalmasin deye
id      name
1	Teacher 1
2	Teacher 2
student_teacher table-inda bunlari birlesidirik ancag bura nese set ede bilmirik komanda seklinde  yazirig bununcun once
librarymanagementsystemdb uzeri sag click default secirik sonra komandani el ile yazib run edirik bu sekilde
insert into student_teacher(student_id, teacher_id) values(1,1);
sonra 2 cini artiririg
insert into student_teacher(student_id, teacher_id) values(1,2);
ve student_teacher table-na baxirig bele bir sey oturur
teacher_id      student_id
1                        1
2                        1
browserde networkde baxirig data gelir ancag rekursiya emele gelir bunu niye edir ve helli nedir sonra baxacagiq ona
gore student_teacher deki datalari silirik
delete from librarymanagementsystemdb.student_teacher where student_id=1;
yazib sildik.
"browserde inspect edende resti networkde goruruk , acnag javascriptin problemi falan olarsa o console-da gorsenir."
Update duymesine inspectde baxirig set olmayib yeni :id , :name falan silinib yerine gelen obyektin deyerleri yazilmayib
bununcun de replace yerine replaceAll edirik hamisini ve sonra baxsag goreceyik ki deyerler oturub.Ancag update-e
basanda popop dolu gelmir cunki Serxan deyende variable kimi nezerde tutur ona gore :name dirnag icinde olmalidir
':name' ve ':surname'-i(string olanlari) dirnaga saldig digerlerine ehtiyac yoxdur.
Restfulu her defe ekrana verib yoxlamag olmur , backend developerin bunu etmesi umumiyyetle duzgun deyil ona gore
postman deye tool var her defe oradan sorgu gonderib cavab alinir. Indi teachers-e baxag bayag yazmag isteyende
rekursiya olmusdu.StudentEntity-e daxil olurug bunun icinde TeacherEntity var hemcinin TeacherEntity-de StudentEntity
var. Json obyekti qurulanda da girir StudentEntity-e baxir deyisenlere qurur sonra gorur TeacherEntity obyektini ora
daxil olur ordaki deyisenleri de qurur.Ic ice girir sonsuz dovr edir. Duzdu StudentEntity-de SchoolEntity de var buna da
daxil olsag gorerik burda da StudentEntity var burda problem olmadi ancag sebebini axtarsag tapmag olar burda niye
rekursiya olmadi ancag biz bayag ki probleme baxirig.Bu problemin qarsisini almag ucun dto anlayisdan istifade edilir.
Demeli teachermanagement folderinde dto folderi acirig ve burda da StudentDto, TeacherDto, SchoolDto claslarini acirig.
Icerisinde ne lazimdi hamsini qeyd edirik entitydeki olan claslara uygun olarag (yeni StudentDto StudentEntity-e uygun
ve digerleri de ele). TeacherDto ve SchoolDto da cox yox sadece id ve name deyisenlerini yazirig, StudentDto-da id,
name, surname, age ,scholarship birde school ve teacherList (Listin tipini TeacherDto edirik tebii ki, entity-de
TeacherEntity idi) deyisenlerini de yazirig ve hamsinin get set metodlarini acirig. Controllerde StudentEndpoint-de
student-i qaytaranda index metodunda StudentEntity yox StudentDto qaytaririg ona gore burdaki liste StudentDto yazirig
ve findAll dan bize StudentEntity  qayidir ve liste oturur-
    @RequestMapping(value = "/students", method= {RequestMethod.GET})
    public List<StudentDto> index(@RequestParam (required = false) String name,
                                  @RequestParam(required = false) String surname,
                                  @RequestParam(required = false) Integer age,
                                  @RequestParam(required = false) BigDecimal scholarship
    ){
        List<StudentEntity> list= studentService.findAll(
                name,
                surname,
                age,
                scholarship
        );
        List<StudentDto> result = new ArrayList<>();
        for(StudentEntity st: list){
            StudentDto studentDto = new StudentDto();
            studentDto.setId(st.getId());
            studentDto.setAge(st.getAge());
            studentDto.setName(st.getName());
            studentDto.setSurname(st.getSurname());
            studentDto.setScholarship(st.getScholarship());

            SchoolDto schoolDto = new SchoolDto();
            schoolDto.setId(st.getSchool().getId());
            schoolDto.setName(st.getSchool().getName());
            studentDto.setSchool(schoolDto);

            List<TeacherDto> teacherDtos= new ArrayList<>();
            for(TeacherEntity teacher: st.getTeacherList()){
                TeacherDto teacherDto = new TeacherDto();
                teacherDto.setId(teacher.getId());
                teacherDto.setName(teacher.getName());
                teacherDtos.add(teacherDto);
            }
            studentDto.setTeacherList(teacherDtos);
            result.add(studentDto);
        }
        return result;
    }
Result StudentDto qurdu, forda StudentEntity ile studendto-nu  bir bir set eledik axirda da studentDto-nu result-a
menimsetdik ve return eledik result-i. Hazirda entity ve dto var, dto-nun icinde ekrana ne vermek isteyirikse onlar var
meselen schooldto-nun icinde bir cox sey ola biler ancag biz id ile name istedik, hemcinin teacherden student-e
rekursiya vermesin deye teacherin icinde studentleri yazmadig. Buna deyirler n+1 problemi bu problemi de dto ile fix
edirler. Bunun 1 ci ustunluyu odur ki ekrana ne vermek isteyirsense onlari idare ede bilirsen, 2 ci ustunluyu odur ki
deyisen adlari ferqli ola biler ekranda meselen name yerine ad yazilmasini isteyerler bunu dto ile edirsen ancag
entity-de deyismeye ehtiyac yoxdur.Basqa bir ustunluk meselen bizden composition isteye bilerler yeni schoolun
melumatlarini StudentDto-nun icinde  SchoolDto obyekti kimi verme, meselen deyerler bele ver -private String schoolName;
birbasa StudenDto icinde olsun.Bele bir sey olsa ne olacag? Bu cure elan edeceyik sadece gelib StudentEndpoint-de set
edende SchoolDto qurub set etmek evezine birbasa studentDto-da setSchoolName deyeceyik, yeni rahatcilig olur.Qisasi
ekrana verdiyin ile iceride gorduyun is bir birinden ferqlenmelidir yeni ekrana verdiyin ayrica claslar olmalidir. Bezen
is yerlerinde bu sehv edirler.Dto var birde ResponseDto var, bizim bu dtomuz response sayilir yeni ekrana ne versen
response sayilir, sadece dto ise entity-nin birbasa ekvivalenti olur.Bunun da ustunlukleri var. Normalda her bir
entity-nin dtosu qurulur ve entity ile dto bire bir eyni olur.Birde view layer ucun controller ucun dto qurulur.
Controllerde bir dto paketi acdig ve evvel acdigimiz butun dto claslarini copy edib bura atirig ve adini deyisib
response edirik (StudentResponseDto ve s. kimi) bunlar ferqlenmelidir cunki cox deyisiklikler edile biler burda.
entity ile eyni olan dto ise - proyekt esnasinda bir yerden diger obyekt oturub meselen gotur bu school-u filan sey ele
deyirsense bu zaman dtolar oturulmelidir, entityler yox.Entity sadece repository-ni cagiranda oturulmelidir. Elave u
stunluyu sabah hansisa bir mentiq qurmag isteyende onu bu sade dto-nun icinde yaza bilirsen meselen calculate salary
qurursan onu burda ede bilersen ancag entity icinde duzgun olmaz.Entity ele bir seydirki baza ile bunun arasinda hec ne
ferqlenmemilidir, qeyri adi hecne olmamalidir, ancag dto uzerinde onu ede bilirsen ona gore mutleq dtolar olmalidir ve
dto entitye cevirilmelidir- bu ne demekdir? meselen bizde StudentService var bu service birbasa StudentEntity qaytarir
(findAll metodunda) amma normalda StudentService geriye StudentDto qaytarir, StudentRepository ise geriye
StudentEntity-sini qaytarir.Proses bele getmelidir sadece dto anlayisini kecmemsidik deye StudentEntity qaytarirdig
findAll-da.Ona gore indi deyisiklikleri edek-
     public List<StudentDto> findAll(String name, String surname, Integer age,
                                    BigDecimal scholarship){
        List<StudentEntity> list;
        if(isAllEmpty(name, surname, age, scholarship))
            list= studentRepository.findAll();
        else
            list= studentRepository.findByNameOrSurnameOrAgeOrScholarship(name, surname, age, scholarship);

        List<StudentDto> result= new ArrayList<>();
        for(StudentEntity st: list){
            StudentDto studentDto = new StudentDto();
            studentDto.setId(st.getId());
            studentDto.setAge(st.getAge());
            studentDto.setName(st.getName());
            studentDto.setSurname(st.getSurname());
            studentDto.setScholarship(st.getScholarship());

            SchoolDto schoolDto = new SchoolDto();
            schoolDto.setId(st.getSchool().getId());
            schoolDto.setName(st.getSchool().getName());
            studentDto.setSchool(schoolDto);

            List<TeacherDto> teacherDtos= new ArrayList<>();
            for(TeacherEntity teacher: st.getTeacherList()){
                TeacherDto teacherDto = new TeacherDto();
                teacherDto.setId(teacher.getId());
                teacherDto.setName(teacher.getName());
                teacherDtos.add(teacherDto);
            }
            studentDto.setTeacherList(teacherDtos);
            result.add(studentDto);
        }
        return result;
    }
Demeli StudentDto etdik sonra ifden yuxari List<StudentEntity> list acirig ve sertlerde return evezine liste
menimsedirik.Sonra bayag yazdigimiz StudentEndpointdeki result haqq hesabini goturub bura yaziriq yeni mappingimzi
(entity-den goturub set elemek) edirik.Mapping biraz zulmludur bunun qarsisini mapstruct ile almag olur , mapstruct
avtomatik set eleyir entity-ni dto-a.
Sonra gelib StudentControllerde index metodundaki listi de StudentDto edirik-
List<StudentDto> list= studentService.findAll {...
Endpoint-de gelib findAll menimsedilen listi StudentDto edirik , result-i StudentResponseDto, fordaki StudentEntity-ni
StudentDto edirik.StudentDto obyekti de StudentResponseDto edirik. Bayaglari entity-den dto duzeltmisdik indi dto-dan
response dto duzeldirik. hemcinin asagida teacherdto ve schooldtolar da deyisiklik edirsen-

    @RequestMapping(value = "/students", method= {RequestMethod.GET})
    public List<StudentResponseDto> index(@RequestParam (required = false) String name,
                                  @RequestParam(required = false) String surname,
                                  @RequestParam(required = false) Integer age,
                                  @RequestParam(required = false) BigDecimal scholarship
    ){
        List<StudentDto> list= studentService.findAll(
                name,
                surname,
                age,
                scholarship
        );
        List<StudentResponseDto> result = new ArrayList<>();
        for(StudentDto st: list){
            StudentResponseDto studentDto = new StudentResponseDto();
            studentDto.setId(st.getId());
            studentDto.setAge(st.getAge());
            studentDto.setName(st.getName());
            studentDto.setSurname(st.getSurname());
            studentDto.setScholarship(st.getScholarship());

            SchoolResponseDto schoolDto = new SchoolResponseDto();
            schoolDto.setId(st.getSchool().getId());
            schoolDto.setName(st.getSchool().getName());
            studentDto.setSchool(schoolDto);

            List<TeacherResponseDto> teacherDtos= new ArrayList<>();
            for(TeacherDto teacher: st.getTeacherList()){
                TeacherResponseDto teacherDto = new TeacherResponseDto();
                teacherDto.setId(teacher.getId());
                teacherDto.setName(teacher.getName());
                teacherDtos.add(teacherDto);
            }
            studentDto.setTeacherList(teacherDtos);
            result.add(studentDto);
        }
        return result;
    }
Toparlayag bir daha controllerin oz dtosu var hamisinin suffix-si response ile bitir.Service Repositoryden
ferqlenmelidir, service dto qaytarmali, repository entity qaytarmalidir.Service de biz entityleri dto cevirib ve onu
qaytardig. Endpointde service.findAll dan studentdto goturduk cunki service =-i cagirdig ve bunnanda response dto
duzeltdik ve geriye de bununqaytardig. Eziyyet olsada ustunluyu coxdur sabah responsede(schoolName numune getirdik) nese
deyisende rahat olacag.
StudentDto-da ise deyisiklik ede bilmersen , entity-nin eynisi olmalidir ki sen studentDto-nu iceride diger bir metoda
gonderende onun icerisine bezi biznes locikalar yaza bilesen meselen bayag dediyimiz calculate salary.
"Asagida run console falan olan bolmede diagram mode var bu proses nece isleyir , kim kimi cagirir falan onlari
gosterir.Spring frameworkden baslayarag bizimde proyektin prosesini gosterdi".
Student teachere yeniden insert edirik workbencde-
insert student_teacher(student_id, teacher_id) values(1,1);
insert student_teacher(student_id, teacher_id) values(2,1);
Browserde baxsag goreceyik ki Teacher 1 adli muellim student-e oturdu.Inspect-de baxsag gorerik artig school ve
teachelist icinde student yoxdur.Belelikle istediyimiz qaytardig response da sabah misal deseler teacherlist yox teacher
qaytar gedib response da deyiseceyik.Yeni is qati ile goruntu qatini ayirin.Sade dto-lar biznes qati gedir,
StudentService mutleq nese oturende bura dto-ya oturulmelidir.Meselen service-de bir metod yazmisan bu qebul etmelidir
StudentDto-
public void doSomething(StudentDto studentDto){

}
Bu bir is gorur deye biznes layer gedir, response da goruntu layer-da istifade olunur.Cetin olur duzdu bir clasi diger
clasa cevirmek bunu da eslinde bele edirler is sadelessin deye- meselen StudentService-de StudentEntity-den StudentDto
qurmag istemisik hemen orda yazidigimiz result haqq hesabini gotururuk ordan StudentDto-da gelib bir metod yazib onun
icine atirig xirda deyisiklikler ederek-
public static StudentDto instance(StudentEntity st){
        StudentDto studentDto = new StudentDto();
        studentDto.setId(st.getId());
        studentDto.setAge(st.getAge());
        studentDto.setName(st.getName());
        studentDto.setSurname(st.getSurname());
        studentDto.setScholarship(st.getScholarship());

        SchoolDto schoolDto = new SchoolDto();
        schoolDto.setId(st.getSchool().getId());
        schoolDto.setName(st.getSchool().getName());
        studentDto.setSchool(schoolDto);

        List<TeacherDto> teacherDtos= new ArrayList<>();
        for(TeacherEntity teacher: st.getTeacherList()){
            TeacherDto teacherDto = new TeacherDto();
            teacherDto.setId(teacher.getId());
            teacherDto.setName(teacher.getName());
            teacherDtos.add(teacherDto);
        }
        studentDto.setTeacherList(teacherDtos);
        return studentDto;
}

Ve Service-de goturduyumuz result haqq  hesabinda (findAll metodunda) onu cagiririg-
  public List<StudentDto> findAll(String name, String surname, Integer age,
                                  BigDecimal scholarship){
        List<StudentEntity> list;
        if(isAllEmpty(name, surname, age, scholarship))
            list= studentRepository.findAll();
        else
            list= studentRepository.findByNameOrSurnameOrAgeOrScholarship(name, surname, age, scholarship);

        List<StudentDto> result= new ArrayList<>();
        for(StudentEntity st: list){
            result.add(StudentDto.instance(st));
        }
        return result;
   }
Meselen StudentDto-da bele bir metod qurmag isteyirem-
public String getFullname(){
        return name+" "+surname;
}
entity-de ise bunu ede bilmerem cunki entity-de yazdigin ger sey table ile elaqesini ifade edir, bele bir sey yazsag ele
bilecek table-da hansisa bir sutun var. schoolname (StudentDto-da) deyisenini de sildik hazirda lazim deyil.
StudentEndpoint yazdigimiz resul haqq hesabini indi sadelesdirek- burda studentdto-dan responsedto qurmag istemisik yene
hemen qayda da kesib gotururuk StudentResponseDto clasinda instance metodu acib(StudentDto parametrini otururuk cunki
bundan qurur) orda yazirig bezi xirda deyisikliker ederek(meselen studentDto deyiseninin adini deysib studentResponseDto
yazdig)-
public static StudentResponseDto insatance(StudentDto st){
        StudentResponseDto studentResponseDto = new StudentResponseDto();
        studentResponseDto.setId(st.getId());
        studentResponseDto.setAge(st.getAge());
        studentResponseDto.setName(st.getName());
        studentResponseDto.setSurname(st.getSurname());
        studentResponseDto.setScholarship(st.getScholarship());

        SchoolResponseDto schoolDto = new SchoolResponseDto();
        schoolDto.setId(st.getSchool().getId());
        schoolDto.setName(st.getSchool().getName());
        studentResponseDto.setSchool(schoolDto);

        List<TeacherResponseDto> teacherDtos= new ArrayList<>();
        for(TeacherDto teacher: st.getTeacherList()){
            TeacherResponseDto teacherDto = new TeacherResponseDto();
            teacherDto.setId(teacher.getId());
            teacherDto.setName(teacher.getName());
            teacherDtos.add(teacherDto);
        }
        studentResponseDto.setTeacherList(teacherDtos);
        return studentResponseDto;
}
"Kod yazanda namedlere(adlandirmalara) fikir verin, sabah onu oxuyan adam basa dussun ne yazmisan"
Sonra StudentEndpointde goturduyumuz result haqq hesabinin yerinde(index metodunda) bu metodu cagiririg-

@RequestMapping(value = "/students", method= {RequestMethod.GET})
public List<StudentResponseDto> index(@RequestParam (required = false) String name,
                              @RequestParam(required = false) String surname,
                              @RequestParam(required = false) Integer age,
                              @RequestParam(required = false) BigDecimal scholarship
){
     List<StudentDto> list= studentService.findAll(
            name,
            surname,
            age,
            scholarship
     );
     List<StudentResponseDto> result = new ArrayList<>();
     for(StudentDto st: list){
         result.add(StudentResponseDto.insatance(st));
     }
     return result;
}
*/
