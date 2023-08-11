-- Places
INSERT INTO places(name) VALUES ('San José'), ('La Soledad'), ('Datuan'), ('Salvacion'), ('Karmai'), ('Chicago'), ('Moneghetti'), ('Kiruna'), ('Arco de Baúlhe'), ('Jinshi'), ('Kochevo'), ('Радолишта'), ('Druya'), ('Guintubhan'), ('Napnapan'), ('Viking'), ('Itororó'), ('Carolina'), ('Bifeng'), ('Datong'), ('Pār Naogaon'), ('Dalmacio Vélez Sársfield'), ('Dālbandīn'), ('Qiling'), ('Szeged'), ('Błonie'), ('Xiabaishi'), ('Bogdaniec'), ('Tha Ruea'), ('Kitami'), ('Horrom'), ('Litibakul'), ('Lexington'), ('San Ignacio de Tupile'), ('Iznoski'), ('Brie-Comte-Robert'), ('Täby'), ('Abade de Neiva'), ('Yefimovskiy'), ('Landskrona'), ('Övertorneå'), ('Taldykorgan'), ('Makoba'), ('Huanghua'), ('Valsamáta'), ('Yegor’yevsk'), ('Tsuruoka'), ('Pochep'), ('Nangger'), ('Mocupe');

-- Decrypted password 1234
INSERT INTO users(email, password, role) VALUES
    ('p@gmail.com', '$2a$10$U/zS6r/bc1MIS5UndON5C.CAVtDePKbyoU/nyrYXIc3PnpuJUo6bu', 'PUBLISHER'),
    ('c@gmail.com', '$2a$10$U/zS6r/bc1MIS5UndON5C.CAVtDePKbyoU/nyrYXIc3PnpuJUo6bu', 'CANDIDATE');
    -- ('a@gmail.com', '$2a$10$bpIjwdoLaiJnTS3GkO0bfODdklwUd/nzAnI6oclhJzd3AFRJrK5W6', 'ADMIN');

INSERT INTO publishers(company_name, description, logo_url, visible, created_at, user_id) VALUES
    ('Pruebita S.A.S', 'Pellentesque porta placerat tortor venenatis consectetur. Nam suscipit mauris in aliquet tempor. Pellentesque sit amet molestie elit. In tempus, eros at auctor convallis, magna risus tristique massa, porta elementum risus neque sed velit. Praesent semper elit eget nisl hendrerit, sed viverra justo sodales. Vestibulum sollicitudin arcu id tempus rhoncus. Ut porta sollicitudin quam, nec aliquet risus elementum a. Nulla facilisi. Donec quis lacinia mi, accumsan laoreet turpis. Cras at lacinia urn.',
    'files-upload/logo_Pruebita.png', 1, now(), 1);

INSERT INTO persons(first_name, last_name, birth_date, phone_number, description,
    preferred_modality, photo_url, address, cv1url, position, created_at, user_id, place_id) VALUES
    ('Jhon Jefferson', 'Doe Smith', '1999-11-21', '123456789', 'Pellentesque porta placerat tortor venenatis consectetur. Nam suscipit mauris in aliquet tempor. Pellentesque sit amet molestie elit. In tempus, eros at auctor convallis, magna risus tristique massa, porta elementum risus neque sed velit. Praesent semper elit eget nisl hendrerit, sed viverra justo sodales. Vestibulum sollicitudin arcu id tempus rhoncus. Ut porta sollicitudin quam, nec aliquet risus elementum a. Nulla facilisi. Donec quis lacinia mi, accumsan laoreet turpis. Cras at lacinia urn.',
    'REMOTE', 'files-upload/profile_l@gmail.com.png', 'Calle 7b # 9-62', 'files-upload/cv1_l@gmail.com.pdf', 'Desarrollador Java Junior',
    now(), 2, 17);

INSERT INTO job_histories(description, position, init_date, end_date, person_id) VALUES
    ('Pellentesque porta placerat tortor venenatis consectetur. Nam suscipit mauris in aliquet tempor.',
    'Desarrollador Java Junior', now(), now(), 1);