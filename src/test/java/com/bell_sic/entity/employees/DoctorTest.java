package com.bell_sic.entity.employees;

import com.bell_sic.entity.*;
import javassist.bytecode.DuplicateMemberException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.time.LocalDate;
import java.time.Period;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(DoctorTest.CustomWatcher.class)
class DoctorTest {
    private final Doctor doc = new Doctor("ciccio", "ciccia", "lauralex", "coccode",
            LocalDate.now(), "bronte");
    private final Operation operation = new Operation("example", OperationType.SURGERY);
    private final Appointment appointment = new Appointment(LocalDate.now(), operation,
            new Patient("example", "example", LocalDate.now(), "bronte"));


    @Test
    @DisplayName("Check appointment registration/insertion")
    @Order(1)
    void addAppointment() throws DuplicateMemberException {
        doc.addAppointment(appointment);
        // check if the method throws a Duplicate Member exception
        Assertions.assertThrows(DuplicateMemberException.class, () -> doc.addAppointment(appointment),
                "Duplicate member check failed!");
        Assertions.assertTrue(doc.getAppointments().contains(appointment), "Appointment insertion failed");
    }

    @Test
    @DisplayName("Check appointment confirmation")
    @Order(2)
    void confirmAppointment() {
        var appointment = doc.getAppointments().stream().findAny();
        Assertions.assertTrue(appointment.isPresent(), "Appointment not present!");
        doc.confirmSelectedAppointment(appointment.get());

        Assertions.assertSame(AppointmentState.ACCEPTED, appointment.get().getAppointmentState(), "Appointment not accepted!");
    }

    @Test
    @DisplayName("Check rehabilitation")
    @Order(3)
    void registerRehabilitation() {
        var appointment = doc.getAppointments().stream().findAny();
        Rehabilitation rehabilitation = new Rehabilitation(appointment.get().getPatient(),
                "example", Period.ofDays(5));
        doc.addRehabilitation(rehabilitation);
        Assertions.assertTrue(doc.getRehabilitations().contains(rehabilitation));

    }

    static class CustomWatcher implements TestWatcher, BeforeTestExecutionCallback {
        @Override
        public void testFailed(ExtensionContext context, Throwable cause) {
            context.getParent().ifPresent(extensionContext ->
                    extensionContext.getStore(ExtensionContext.Namespace.create(getClass())).put("failed", true));
        }

        @Override
        public void beforeTestExecution(ExtensionContext context) {
            context.getParent().ifPresent(extensionContext ->
                    Assumptions.assumeFalse(extensionContext.getStore(ExtensionContext.Namespace.create(getClass())).getOrDefault(
                            "failed", boolean.class, false
                    ), "Previous tests failed!"));
        }
    }

}