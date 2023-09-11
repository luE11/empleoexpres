-- candidate_related_studies view
CREATE VIEW candidate_related_studies
    AS SELECT row_number() over (order by s.certificate_name) id, s.certificate_name, ps.entity_name, ps.start_date, ps.end_date, ps.description, +
            ps.person_id
            FROM persons p, persons_has_studies ps, studies s
            WHERE p.person_id=ps.person_id AND ps.study_id=s.study_id;

-- candidate_applied_jobs view
CREATE VIEW candidate_applied_jobs
    AS SELECT row_number() over (order by j.pub_date) id, j.title, +
    j.job_mode, jc.state, jc.company_observations, p.company_name, +
    jc.candidate_comment, jc.cv_url, jc.updated_at, jc.person_id, j.job_id
            FROM jobs_has_candidates jc, jobs j, publishers p
            WHERE jc.job_id=j.job_id AND j.publisher_id=p.publisher_id;

-- applications view
CREATE VIEW applications
    AS SELECT row_number() over (order by p.person_id) id, CONCAT(p.first_name, ' ', p.last_name) as name,
           	jc.state, jc.cv_url, jc.updated_at, jc.job_id, p.person_id
               FROM jobs_has_candidates jc, persons p
               WHERE jc.person_id=p.person_id;

-- Insert test data

-- Places
INSERT INTO places(name) VALUES ('San José'), ('La Soledad'), ('Datuan'), ('Salvacion'), ('Karmai'), ('Chicago'), ('Moneghetti'), ('Kiruna'), ('Arco de Baúlhe'), ('Jinshi'), ('Kochevo'), ('Радолишта'), ('Druya'), ('Guintubhan'), ('Napnapan'), ('Viking'), ('Itororó'), ('Carolina'), ('Bifeng'), ('Datong'), ('Pār Naogaon'), ('Dalmacio Vélez Sársfield'), ('Dālbandīn'), ('Qiling'), ('Szeged'), ('Błonie'), ('Xiabaishi'), ('Bogdaniec'), ('Tha Ruea'), ('Kitami'), ('Horrom'), ('Litibakul'), ('Lexington'), ('San Ignacio de Tupile'), ('Iznoski'), ('Brie-Comte-Robert'), ('Täby'), ('Abade de Neiva'), ('Yefimovskiy'), ('Landskrona'), ('Övertorneå'), ('Taldykorgan'), ('Makoba'), ('Huanghua'), ('Valsamáta'), ('Yegor’yevsk'), ('Tsuruoka'), ('Pochep'), ('Nangger'), ('Mocupe');

-- Decrypted password 1234
INSERT INTO users(email, password, role) VALUES
    ('p@gmail.com', '$2a$10$U/zS6r/bc1MIS5UndON5C.CAVtDePKbyoU/nyrYXIc3PnpuJUo6bu', 'PUBLISHER'),
    ('c@gmail.com', '$2a$10$U/zS6r/bc1MIS5UndON5C.CAVtDePKbyoU/nyrYXIc3PnpuJUo6bu', 'CANDIDATE'),
    ('p2@gmail.com', '$2a$10$U/zS6r/bc1MIS5UndON5C.CAVtDePKbyoU/nyrYXIc3PnpuJUo6bu', 'PUBLISHER'),
    ('p3@gmail.com', '$2a$10$U/zS6r/bc1MIS5UndON5C.CAVtDePKbyoU/nyrYXIc3PnpuJUo6bu', 'PUBLISHER'),
    ('p4@gmail.com', '$2a$10$U/zS6r/bc1MIS5UndON5C.CAVtDePKbyoU/nyrYXIc3PnpuJUo6bu', 'PUBLISHER'),
    ('p5@gmail.com', '$2a$10$U/zS6r/bc1MIS5UndON5C.CAVtDePKbyoU/nyrYXIc3PnpuJUo6bu', 'PUBLISHER');
    -- ('a@gmail.com', '$2a$10$bpIjwdoLaiJnTS3GkO0bfODdklwUd/nzAnI6oclhJzd3AFRJrK5W6', 'ADMIN');

INSERT INTO publishers(company_name, description, logo_url, visible, created_at, user_id) VALUES
    ('Pruebita S.A.S', 'Pellentesque porta placerat tortor venenatis consectetur. Nam suscipit mauris in aliquet tempor. Pellentesque sit amet molestie elit. In tempus, eros at auctor convallis, magna risus tristique massa, porta elementum risus neque sed velit. Praesent semper elit eget nisl hendrerit, sed viverra justo sodales. Vestibulum sollicitudin arcu id tempus rhoncus. Ut porta sollicitudin quam, nec aliquet risus elementum a. Nulla facilisi. Donec quis lacinia mi, accumsan laoreet turpis. Cras at lacinia urn.',
    'files-upload/logo_Pruebita.png', 1, now(), 1),
    ('Publisher 2 company', 'Nam suscipit mauris in aliquet tempor. Pellentesque sit amet molestie elit. In tempus, eros at auctor convallis, magna risus tristique massa, porta elementum risus neque sed velit. Praesent semper elit eget nisl hendrerit, sed viverra justo sodales. Vestibulum sollicitudin arcu id tempus rhoncus. Ut porta sollicitudin quam, nec aliquet risus elementum a. Nulla facilisi. Donec quis lacinia mi, accumsan laoreet turpis. Cras at lacinia urn. Pellentesque porta placerat tortor venenatis consectetur.',
        'files-upload/logo_p2.jpg', 1, now(), 3),
    ('Publicador colombiano', 'Pellentesque sit amet molestie elit. In tempus, eros at auctor convallis, magna risus tristique massa, porta elementum risus neque sed velit. Pellentesque porta placerat tortor venenatis consectetur. Nam suscipit mauris in aliquet tempor. Praesent semper elit eget nisl hendrerit, sed viverra justo sodales. Vestibulum sollicitudin arcu id tempus rhoncus. Ut porta sollicitudin quam, nec aliquet risus elementum a. Nulla facilisi. Donec quis lacinia mi, accumsan laoreet turpis. Cras at lacinia urn.',
        'files-upload/logo_p3.png', 0, now(), 4),
    ('Pruebita Enterprises', 'Praesent semper elit eget nisl hendrerit, sed viverra justo sodales. Vestibulum sollicitudin arcu id tempus rhoncus. Ut porta sollicitudin quam, nec aliquet risus elementum a. Nulla facilisi. Donec quis lacinia mi, accumsan laoreet turpis. Cras at lacinia urn. Pellentesque porta placerat tortor venenatis consectetur. Nam suscipit mauris in aliquet tempor. Pellentesque sit amet molestie elit. In tempus, eros at auctor convallis, magna risus tristique massa, porta elementum risus neque sed velit.',
        'files-upload/logo_p4.png', 1, now(), 5),
    ('Little Testing LTDA', 'Vestibulum sollicitudin arcu id tempus rhoncus. Ut porta sollicitudin quam, nec aliquet risus elementum a. Nulla facilisi. Donec quis lacinia mi, accumsan laoreet turpis. Cras at lacinia urn. Pellentesque porta placerat tortor venenatis consectetur. Nam suscipit mauris in aliquet tempor. Pellentesque sit amet molestie elit. In tempus, eros at auctor convallis, magna risus tristique massa, porta elementum risus neque sed velit. Praesent semper elit eget nisl hendrerit, sed viverra justo sodales.',
        'files-upload/logo_p5.jpg', 1, now(), 6);

INSERT INTO persons(first_name, last_name, birth_date, phone_number, description,
    preferred_modality, photo_url, address, cv1url, position, created_at, user_id, place_id) VALUES
    ('Jhon Jefferson', 'Doe Smith', '1999-11-21', '123456789', 'Pellentesque porta placerat tortor venenatis consectetur. Nam suscipit mauris in aliquet tempor. Pellentesque sit amet molestie elit. In tempus, eros at auctor convallis, magna risus tristique massa, porta elementum risus neque sed velit. Praesent semper elit eget nisl hendrerit, sed viverra justo sodales. Vestibulum sollicitudin arcu id tempus rhoncus. Ut porta sollicitudin quam, nec aliquet risus elementum a. Nulla facilisi. Donec quis lacinia mi, accumsan laoreet turpis. Cras at lacinia urn.',
    'REMOTE', 'files-upload/profile_l@gmail.com.png', 'Calle 7b # 9-62', 'files-upload/cv1_l@gmail.com.pdf', 'Desarrollador Java Junior',
    now(), 2, 17);

INSERT INTO job_histories(description, position, init_date, end_date, person_id) VALUES
    ('Pellentesque porta placerat tortor venenatis consectetur. Nam suscipit mauris in aliquet tempor.',
    'Desarrollador Java Junior', now(), now(), 1);

INSERT INTO studies(certificate_name) VALUES
	('Ingeniero de sistemas'), ('Desarrollador de Software'), ('Abogado'), ('Licenciado en educación básica'), ('Especialista en desarrollo de sistemas');

INSERT INTO persons_has_studies (entity_name, start_date, end_date, description, person_id, study_id) VALUES
    ('Universidad Prueba Ejemplar - UPE', now(), now(), 'Pellentesque porta placerat tortor venenatis consectetur nam suscipit mauris.',
    1, 1);

INSERT INTO `jobs` (`description`, `job_mode`, `pub_date`, `salary`, `state`, `title`, `years_of_experience`, `place_id`, `profession_id`, `publisher_id`) VALUES
('Requisitos:\r\nRequerimos para nuestro equipo de trabajo Técnico, Tecnólogo, Estudiante o Profesional en Administración de Empresas, Finanzas, Contaduría, Economía o carreras afines.\r\ncon dominio de herramientas ofimáticas especialmente del paquete office con énfasis en Excel (Avanzado).\r\n\r\nActividades a realizar:\r\nBrindar un excelente servicio a los clientes internos y externos de la Empresa mediante la atención oportuna de todas sus solicitudes, y la distribución y confirmación de los trámites que sean requeridos ante las áreas asignadas.\r\nEl candidato ideal deberá ser proactivo(a), con adquisición rápida de conocimientos trabajo en equipo y excelentes relaciones interpersonales.\r\n\r\nExperiencia:\r\nCon o sin Experiencia.\r\n\r\nCondiciones:\r\nEstabilidad Laboral\r\nTiempo completo\r\nContrato a termino Indefinido\r\nLugar de trabajo Motavita (Boy)\r\n\r\nTipo de puesto: Tiempo completo, Indefinido\r\n\r\nSalario: $1.160.000 al mes',
'FACETOFACE', now(), 1160000, 'ACTIVE', 'Asistente de Gerencia', 0, 47, 3, 1),
('Dolor nemo aliquam nam rerum perspiciatis aspernatur eius. Id eius molestiae enim molestiae enim sunt. Excepturi iusto explicabo rerum veritatis.','HYBRID','2023-08-17 11:13:01.000000',1000000,'ACTIVE','Corrupti illum aut quia enim id corporis doloremque.',0, 46, 3, 2),
('Aspernatur hic officia adipisci quibusdam nesciunt quia. Ut accusantium et incidunt occaecati. Ea magnam quidem hic mollitia sunt soluta. Veniam occaecati ab et provident nostrum dignissimos.','ALL','2023-08-17 11:13:01.000000',4,'ACTIVE','Nam repudiandae expedita dolor debitis ea ea.',0, 45, 3, 3),
('Accusamus hic molestiae aliquam veritatis iusto sed consequatur. Aut fugiat dolorem asperiores omnis ut autem ex. Nihil magni veritatis eum similique voluptatibus sint atque.','ALL',now(),35357932,'ACTIVE','Magni voluptatibus tempore eveniet.',0.5, 44, 3, 4),
('Voluptatem aut et occaecati labore facilis odit. Sed aut eos quia cum ut. Accusamus natus omnis est eaque minus assumenda. Velit consequatur ab ipsum debitis.','FACETOFACE','2023-08-17 11:13:01.000000',53027830,'ACTIVE','Quaerat reiciendis dolorem enim deserunt.',1, 43, 3, 5),
('Voluptatum necessitatibus vel accusantium qui perferendis maiores. Et qui commodi voluptate a. Sit rerum temporibus sit laborum facilis. Quibusdam vero maiores maxime et.','HYBRID','2023-08-17 11:13:01.000000',17877987,'CLOSED','Ut totam accusantium non hic ex eaque.',3, 40, 3, 1),
('Consectetur omnis modi qui laboriosam omnis eos quo. Totam et enim et a voluptas iste. Sed dolores et odit magni ab at voluptate. Ex molestias sit ut eum deserunt enim.','REMOTE',now(),58152651,'ACTIVE','Aut corrupti delectus dolorem incidunt vero laborum et et.',5, 40, 3, 2),
('Ut hic ratione et facere et. Accusantium reiciendis pariatur voluptatibus non. Quae aut qui rem.','FACETOFACE','2023-08-17 11:13:01.000000',4718569,'CLOSED','Quod voluptas quia dolor.',1.5, 39, 3, 3),
('Corporis ex tenetur molestiae ut. Delectus voluptates quis architecto velit corrupti nam repudiandae. Perspiciatis tempora sint et.','ALL','2023-08-17 11:13:01.000000',2000000,'ACTIVE','Vel nemo sit numquam quo praesentium laborum.',2.5, 38, 3, 4),
('Consequatur libero eaque nostrum eos omnis aut velit. Voluptas ipsa molestias neque ex sed rerum. Dignissimos est provident suscipit dolor eius sed. Autem et qui consequatur quis facilis temporibus.','ALL','2023-08-17 11:13:01.000000',865000,'ACTIVE','Repellendus et velit nesciunt possimus sint ratione dicta esse.',0, 37, 3, 5),
('Officia vel sit ut enim dignissimos. Harum sint autem id nihil explicabo nihil reiciendis. Quas beatae doloremque et nam quo ipsum nobis.','ALL','2023-08-17 11:13:01.000000',1380000,'ACTIVE','Maxime ut aut est excepturi ad accusantium.',0, 35, 3, 3),
('Quia ut mollitia quaerat eum. Aut distinctio blanditiis nulla doloribus consequatur provident asperiores placeat. Accusantium est dolores id praesentium accusantium facilis.','FACETOFACE','2023-08-17 11:13:01.000000',391300000,'ACTIVE','Officiis adipisci optio doloribus ut neque.',2, 34, 3, 2),
('Accusamus omnis dolor nam sint. Id quo amet soluta quod iusto sed aut. Id recusandae nostrum possimus dolor et ipsa.','REMOTE','2023-08-17 11:13:01.000000',82454000,'ACTIVE','Eum quo minima sed et.',2, 33, 3, 1),
('Rerum consequatur saepe sed officiis possimus rerum et. Pariatur quam hic unde voluptatem. Ea ut itaque suscipit qui quam laudantium temporibus. Amet sed vero et.','REMOTE','2023-08-17 11:13:01.000000',1559072,'ACTIVE','Rerum modi repellendus qui animi.',0, 32, 3, 4),
('Et sed sint nobis tenetur praesentium. Eveniet iusto cupiditate vitae incidunt animi ut. Voluptatum corporis ipsam reiciendis odio iusto. Nisi odio qui omnis. Iusto qui voluptates numquam ut quae.','FACETOFACE','2023-08-17 11:13:01.000000',8859900,'ACTIVE','Perferendis facilis totam sint nobis.',0.5, 31, 3, 5),
('Dolorem voluptatem veniam voluptatem quo rerum quia eos. Dolorem similique consequuntur totam sit numquam reprehenderit hic. Officia aut libero at ea consequatur nam voluptatem. Aut totam veritatis id voluptas aspernatur repellat nihil qui.','ALL','2023-08-17 11:13:01.000000',23320000,'ACTIVE','Reiciendis commodi consectetur aspernatur velit enim est provident.',0, 45, 3, 1),
('Quibusdam aut numquam id qui. Nesciunt inventore dolorum voluptatum quia laboriosam. Odit beatae itaque commodi omnis omnis odit.','REMOTE','2023-08-17 11:13:01.000000',10000000,'ACTIVE','Consequatur voluptate nesciunt nesciunt modi impedit eos.',0, 44, 3, 2),
('Accusantium ut architecto odio accusantium. Quis qui id laudantium libero harum saepe autem. Sit quisquam dolor distinctio aut praesentium enim.','FACETOFACE','2023-08-17 11:13:01.000000',7100000,'CLOSED','Ipsum aut et ut sapiente velit repellat veniam enim.',0, 43, 3, 3),
('Velit dignissimos in nam et et in accusantium incidunt. Occaecati dignissimos aut et. Sunt dolores sunt maiores eos. Voluptatibus ipsa est totam cum quaerat vel.','ALL','2023-08-17 11:13:01.000000',985440000,'CLOSED','Impedit earum rerum vitae nobis rerum maiores reiciendis.',0, 42, 3, 4),
('Eum explicabo quia consequatur recusandae. Sed odit vero molestias dicta. Rerum dolores qui corporis dolorem. Ipsa qui et fugit sed. Repellendus autem laborum culpa qui fugit ea molestiae.','ALL','2023-08-17 11:13:01.000000',5700000,'ACTIVE','Itaque quam iure aliquam quasi tenetur aut.',0, 41, 3, 5),
('Voluptates unde numquam nesciunt voluptate aliquam ipsum. Quos harum nihil beatae asperiores sint sequi. Atque sit tenetur quia qui velit commodi. Assumenda temporibus soluta magnam earum blanditiis.','REMOTE','2023-08-17 11:13:01.000000',900000,'ACTIVE','Libero sunt blanditiis autem nisi est nobis neque.',0, 40, 3, 4),
('Earum magnam voluptas voluptas ratione. Sunt dolores velit vel neque non magni consequatur aspernatur. Dolor ut nihil quo dolores qui quis. Mollitia explicabo veniam repellendus illum.','FACETOFACE','2023-08-17 11:13:01.000000',31027382,'ACTIVE','Veniam totam rem commodi minima sed.',0, 39, 3, 3),
('Sit veritatis ut minima. Temporibus qui occaecati eligendi atque. Autem aut nihil rerum. Cum quia esse inventore omnis voluptates eveniet ea.','ALL','2023-08-17 11:13:01.000000',5842056,'ACTIVE','Debitis ipsa occaecati dolore qui accusantium eaque.',0, 38, 3, 2),
('Omnis tenetur eius autem qui architecto. Cupiditate quae voluptas consequatur dolorem aut quasi voluptatibus.','ALL','2023-08-17 11:13:01.000000',8565230,'CLOSED','Nesciunt eaque quaerat et sapiente sapiente.',0, 37, 3, 1),
('Distinctio quia molestias est tenetur ex dicta. Et dolor aut sunt molestias et. Est nisi quo quae.','FACETOFACE','2023-08-17 11:13:01.000000',3900082,'ACTIVE','Soluta velit cupiditate beatae laborum eveniet quas.',0, 36, 3, 3),
('Qui cupiditate voluptate beatae molestiae voluptatem ipsa. Unde qui impedit pariatur ratione ex officia minima aut. Est quo consectetur amet repellat.','HYBRID','2023-08-17 11:13:01.000000',8728000,'ACTIVE','Et nihil reiciendis et fugit sed.',0, 35, 3, 3);

INSERT INTO jobs_has_candidates (job_id, person_id, candidate_comment, company_observations, state, cv_url, updated_at)
    VALUES ('1', '1', 'Buenas, estoy interesado en el puesto, creo que soy un candidato ideal y bla bla bla lorem ipsum dolorem. También puede copiar y pegar bloques de texto de un documento que tengas ya escrito.',
    NULL, 'APPLIED', 'files-upload/cv1_l@gmail.com.pdf', '2023-08-31 10:50:00.000000');