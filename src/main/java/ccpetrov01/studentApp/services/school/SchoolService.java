package ccpetrov01.studentApp.services.school;

import ccpetrov01.studentApp.Dtos.SchoolDtoView;
import ccpetrov01.studentApp.Dtos.StudentDtoView;
import ccpetrov01.studentApp.exceptions.GlobalException;
import ccpetrov01.studentApp.exceptions.ResourceNotFoundException;
import ccpetrov01.studentApp.models.School;
import ccpetrov01.studentApp.models.Student;
import ccpetrov01.studentApp.repository.SchoolRepository;
import ccpetrov01.studentApp.requests.UpdateSchoolRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchoolService implements ISchoolService{
    private final SchoolRepository schoolRepository;

    @Override
    public School addSchool(School school) {
        try{
           return schoolRepository.save(school);
        }catch(GlobalException e){
            throw new GlobalException("Something went wrong and object is not saved!", e);
        }
    }

    @Override
    public School getSchoolById(Integer schoolId) {
        return schoolRepository.findById(schoolId)
                .orElseThrow(()-> new ResourceNotFoundException("School with id:" + schoolId + " doesn't exists!"));
    }

    @Override
    public void deleteSchoolById(Integer schoolId) {
        schoolRepository.findById(schoolId).ifPresentOrElse(
                schoolRepository::delete
                , () -> { throw new ResourceNotFoundException("School with id:" + schoolId + " doesn't exists!");
                });
    }

    @Override
    public School updateSchoolInformation(UpdateSchoolRequest request, Integer schoolId) {
        School school = schoolRepository.findById(schoolId)
                .orElseThrow(() -> new ResourceNotFoundException("School with id:" + schoolId + " doesn't exists!"));

        school.setName(request.getName());
        school.setRating(request.getRating());
        school.setAddress(request.getAddress());
        school.setDescription(request.getDescription());

        return schoolRepository.save(school);
    }

    @Override
    public List<School> getAllSchools() {
        try{
           return schoolRepository.findAll();
        }catch(ResourceNotFoundException e) {
            throw new ResourceNotFoundException("There are not available schools yet, try adding one!");
        }
    }

    @Override
    public List<Double> findAllRatingsOrderedAsc() {
        try{
            return schoolRepository.findAllRatingsOrderedAsc();
        }catch(ResourceNotFoundException e){
            throw new ResourceNotFoundException("There are maybe not existing schools yet,try adding one!");
        }

    }

    @Override
    public School findByName(String school_name) {
        try{
            return schoolRepository.findByName(school_name);
        }catch(ResourceNotFoundException e){
            throw new ResourceNotFoundException("There are no school with the given name:" + school_name);
        }
    }

    @Override
    public SchoolDtoView schoolDtoView(School school) {
        SchoolDtoView schoolDtoView = new SchoolDtoView();

        schoolDtoView.setName(school.getName());
        schoolDtoView.setRating(school.getRating());
        schoolDtoView.setAddress(school.getAddress());
        schoolDtoView.setDescription(school.getDescription());

        return schoolDtoView;
    }

    @Override
    public List<SchoolDtoView> schoolDtoViewList(List<School> schoolList) {
        return schoolList
                .stream()
                .map(this::schoolDtoView)
                .toList();
    }
}
